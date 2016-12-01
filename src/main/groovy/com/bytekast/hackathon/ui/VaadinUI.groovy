package com.bytekast.hackathon.ui

import com.bytekast.hackathon.service.BackgroundPushService
import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.server.ExternalResource
import com.vaadin.server.Page
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import org.springframework.beans.factory.annotation.Autowired
import org.vaadin.sliderpanel.SliderPanel
import org.vaadin.sliderpanel.SliderPanelBuilder
import org.vaadin.sliderpanel.client.SliderMode
import org.vaadin.sliderpanel.client.SliderTabPosition

@Theme("hackathon")
@SpringUI(path = "")
@Push
@Widgetset("AppWidgetset")
class VaadinUI extends UI {

  @Autowired
  private BackgroundPushService backgroundPushService

  @Lazy
  Label labelTime

  SliderPanel topSlider
  SliderPanel leftSlider
  BrowserFrame browser
  FilterPanel filterPanel

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    // generate unique id for this instance
    setId(UUID.randomUUID().toString())
    backgroundPushService.registerPushCallback(getId(), this.&update)

    final VerticalLayout mainLayout = new VerticalLayout()
    mainLayout.setSizeFull()

    def filterPanelListener = { Component.Event event ->
      if (filterPanel.isFilterValuesEmpty()) {
        Notification.show('No Dataset Criteria Selected', Notification.Type.ERROR_MESSAGE)
      } else {

        def notification =
            new Notification("Your data set request is being processed",
                'Please wait for the request to be completed',
                Notification.Type.HUMANIZED_MESSAGE)
        notification.setDelayMsec(200)
        notification.show(Page.getCurrent())

        topSlider.collapse()
        browser.markAsDirtyRecursive()

        def noCache = "noCache=${UUID.randomUUID().toString()}"
        def dashboardUrl = detectDashboardUrl(filterPanel.getFilterValues())
        browser.setSource(new ExternalResource("${dashboardUrl}?allow_login_screen=true&${noCache}"))
      }
    }
    filterPanel = new FilterPanel(labelTime, filterPanelListener)

    topSlider = new SliderPanelBuilder(filterPanel)
        .expanded(false)
        .mode(SliderMode.TOP)
        .caption("Create New Dataset")
        .tabPosition(SliderTabPosition.END)
        .flowInContent(true)
        .build()
    mainLayout.addComponent(topSlider)

    browser = new BrowserFrame()
    browser.setCaption('<img class="tendril-logo" src="http://share.rowellbelen.com/bBlP/4NqhgFmI+"/>' +
        '<h2>ENERGY INTELLIGENCE ON-DEMAND</h2>')
    browser.setCaptionAsHtml(true)
    browser.setSource(new ExternalResource("https://tendril.looker.com/embed/dashboards/21?allow_login_screen=true"))
    browser.setSizeFull()

    HorizontalLayout middleContent = new HorizontalLayout()
    middleContent.setSizeFull()

    def listPanel = new ListPanel({ Component.Event event ->
      leftSlider.collapse()
      browser.setSource(new ExternalResource("https://tendril.looker.com/embed/dashboards/1?allow_login_screen=true"))
      browser.markAsDirty() // refresh component
    })
    leftSlider = new SliderPanelBuilder(listPanel)
        .expanded(false)
        .mode(SliderMode.LEFT)
        .caption("Existing Datasets")
        .tabPosition(SliderTabPosition.MIDDLE)
        .flowInContent(true)
        .build()
    //middleContent.addComponent(leftSlider)

    middleContent.addComponent(browser)
    middleContent.setComponentAlignment(browser, Alignment.MIDDLE_CENTER)
    middleContent.setExpandRatio(browser, 1)

    mainLayout.addComponent(middleContent)
    mainLayout.setExpandRatio(middleContent, 1)
    setContent(mainLayout)
  }

  String detectDashboardUrl(Map filterValues) {

    def models = filterValues?.models
    if (models?.any { ['Smart Thermostat Propensity', 'Solar Propensity'].contains(it) }) {
      return 'https://tendril.looker.com/embed/dashboards/22'
    } else {
      return 'https://tendril.looker.com/embed/dashboards/23'
    }
  }

  def update(String time) {
    // push triggered. update value
    access({
      labelTime.setValue(time)
      labelTime.setWidth(null) // center
    })
  }

  @Override
  void detach() {
    // deregister callback before close
    backgroundPushService.deregisterPushCallback(getId())
    super.detach();
  }
}