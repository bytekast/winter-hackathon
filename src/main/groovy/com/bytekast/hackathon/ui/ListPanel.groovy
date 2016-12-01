package com.bytekast.hackathon.ui

import com.vaadin.data.Property
import com.vaadin.ui.*
import com.vaadin.ui.Component.Listener

class ListPanel extends CustomComponent {

  ListPanel(Listener submitListener = null) {
    // A layout structure used for composition
    Panel panel = new Panel('Existing Datasets')
    VerticalLayout panelContent = new VerticalLayout()
    panelContent.setMargin(true) // Very useful
    panel.setContent(panelContent)


    def selectDataset = new ListSelect()
    selectDataset.addItems('Duke 2016 Savings Actions', 'Duke Bills - FL', 'AEP OE Insights - December')
    selectDataset.addValueChangeListener({ Property.ValueChangeEvent event ->
      //Notification.show('Dataset Not Available', Notification.Type.WARNING_MESSAGE)
    })
    panelContent.addComponent(selectDataset)

    def loadButton = new Button('Load', { Button.ClickEvent event ->
      if (submitListener) {
        submitListener.componentEvent(event)
      }

      //Notification.show('Dataset Not Available', 'Unable to connect to the selectes dataset. ' +
      //    'Please try again later', Notification.Type.ERROR_MESSAGE)
    } as Button.ClickListener)
    panelContent.addComponent(loadButton)
    panelContent.setComponentAlignment(loadButton, Alignment.TOP_CENTER)

    // Set the size as undefined at all levels
    panelContent.setSizeFull()
    panel.setSizeFull()
    setSizeFull()

    // The composition root MUST be set
    setCompositionRoot(panel)
  }
}
