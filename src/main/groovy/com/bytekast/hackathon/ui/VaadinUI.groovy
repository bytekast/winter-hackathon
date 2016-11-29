package com.bytekast.hackathon.ui

import com.bytekast.hackathon.service.BackgroundPushService
import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinSession
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.Alignment
import com.vaadin.ui.Label
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import org.springframework.beans.factory.annotation.Autowired

@Theme("valo")
@SpringUI(path = "")
@Push
public class VaadinUI extends UI {

  @Autowired
  private BackgroundPushService backgroundPushService

  @Lazy
  Label labelTime

  @Override
  protected void init(VaadinRequest vaadinRequest) {
    // generate unique id for this instance
    setId(UUID.randomUUID().toString())

    // keep session short
    VaadinSession.getCurrent().getSession().setMaxInactiveInterval(30)

    // create the content root layout for the UI
    VerticalLayout content = new VerticalLayout()
    content.setSizeFull()
    setContent(content)

    // add component to layout
    content.addComponent(labelTime)
    content.setComponentAlignment(labelTime, Alignment.MIDDLE_CENTER)

    // register this.update closure as callback when push is triggered
    // by background process
    backgroundPushService.registerPushCallback(getId(), this.&update)
  }

  def update(String time) {
    // push triggered. update value
    access({
      labelTime.setValue(time)
      labelTime.setWidth(null) // center
    })
  }

  @Override
  public void detach() {
    // deregister callback before close
    backgroundPushService.deregisterPushCallback(getId())
    super.detach();
  }
}