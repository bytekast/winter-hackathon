package com.bytekast.hackathon.ui

import com.vaadin.ui.*
import com.vaadin.ui.Component.Listener
import groovy.transform.Memoized

import java.text.DateFormatSymbols

class FilterPanel extends CustomComponent {

  final Integer MAX_COLUMN_WIDTH = 150
  final Integer MAX_GRID_WIDTH = 175

  FilterPanel(Label label, Listener submitListener = null) {
    // A layout structure used for composition
    Panel panel = new Panel("Filter Data Set")
    VerticalLayout panelContent = new VerticalLayout()
    panelContent.setMargin(true) // Very useful
    panel.setContent(panelContent)

    label = new Label()
    // Compose from multiple components
    label.setSizeUndefined() // Shrink
    //panelContent.addComponent(label)

    def layout = new HorizontalLayout()
    layout.setSpacing(true)

    layout.addComponent(tenantGrid())
    layout.addComponent(modelsGrid())
    layout.addComponent(statesGrid())
    layout.addComponent(yearGrid())
    layout.addComponent(monthsGrid())
    layout.addComponent(daysGrid())
    layout.setSizeUndefined()

    panelContent.addComponent(layout)


    def buttonLayout = new VerticalLayout()
    def button = new Button('Analyze', { event ->
      if (submitListener) {
        submitListener.componentEvent(event)
      }
    } as Button.ClickListener)
    button.addStyleName("default")
    buttonLayout.addComponent(label)
    buttonLayout.addComponent(button)
    buttonLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER)
    buttonLayout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT)
    buttonLayout.setSizeFull()

    panelContent.addComponent(buttonLayout)

    // Set the size as undefined at all levels
    panelContent.setSizeUndefined()
    panel.setSizeFull()
    setSizeFull()

    // The composition root MUST be set
    setCompositionRoot(panel)
  }

  Boolean isFilterValuesEmpty() {
    for (f in getFilterValues()) {
      if (!f.value.isEmpty()) {
        return false
      }
    }
    return true
  }

  def getFilterValues() {
    [
        tenants: getSelectedValues(tenantGrid(), 'TENANT'),
        models : getSelectedValues(modelsGrid(), 'MODEL'),
        state  : getSelectedValues(statesGrid(), 'STATE'),
        year   : getSelectedValues(yearGrid(), 'YEAR'),
        month  : getSelectedValues(monthsGrid(), 'MONTH'),
        days   : getSelectedValues(daysGrid(), 'DAY')
    ]
  }

  def getSelectedValues(Grid grid, String property) {
    def values = []
    grid?.getSelectedRows()?.each {
      def val = grid.getContainerDataSource().getItem(it)?.getItemProperty(property)?.value
      if (val) {
        values << val
      }
    }
    values
  }

  @Memoized
  Grid tenantGrid() {
    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("TENANT", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH)
    def tenants = ['Duke', 'AEP', 'SunPower']
    tenants.each {
      grid.addRow(it)
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setHeightByRows(tenants.size())
    grid.setWidth("${MAX_GRID_WIDTH}px")
    grid
  }

  @Memoized
  Grid modelsGrid() {
    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("MODEL", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH + 150)
    def models = ['Bills',
                  'Energy ID Scores',
                  'Energy Usage (Historical)',
                  'Energy Usage (Predictive)',
                  'Orchestrated Energy',
                  'Real Estate Scores',
                  'Savings Actions',
                  'Smart Thermostat Propensity',
                  'Solar Propensity']
    models.each {
      grid.addRow(it)
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setHeightByRows(models.size())
    grid.setWidth("${MAX_GRID_WIDTH + 150}px")
    grid
  }

  @Memoized
  Grid yearGrid() {
    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("YEAR", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH)
    (2016..2000).each {
      grid.addRow(it.toString())
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setWidth("${MAX_GRID_WIDTH}px")
    grid
  }

  @Memoized
  Grid daysGrid() {
    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("DAY", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH)
    (1..31).each {
      grid.addRow(it.toString())
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setWidth("${MAX_GRID_WIDTH}px")
    grid
  }

  @Memoized
  Grid monthsGrid() {
    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("MONTH", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH)
    new DateFormatSymbols().getMonths().each {
      grid.addRow(it.toString())
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setWidth("${MAX_GRID_WIDTH}px")
    grid
  }

  @Memoized
  Grid statesGrid() {

    // Create a grid
    Grid grid = new Grid()

    // Define some columns
    grid.addColumn("STATE", String.class)
    grid.getColumns().first().setMaximumWidth(MAX_COLUMN_WIDTH)
    states().each {
      grid.addRow(it.abbreviation)
    }

    grid.setSelectionMode(Grid.SelectionMode.MULTI)
    grid.setWidth("${MAX_GRID_WIDTH}px")
    grid
  }


  def states() {
    [
        [name: 'ALABAMA', abbreviation: 'AL'],
        [name: 'ALASKA', abbreviation: 'AK'],
        [name: 'ARIZONA', abbreviation: 'AZ'],
        [name: 'ARKANSAS', abbreviation: 'AR'],
        [name: 'CALIFORNIA', abbreviation: 'CA'],
        [name: 'COLORADO', abbreviation: 'CO'],
        [name: 'CONNECTICUT', abbreviation: 'CT'],
        [name: 'DELAWARE', abbreviation: 'DE'],
        [name: 'DISTRICT OF COLUMBIA', abbreviation: 'DC'],
        [name: 'FLORIDA', abbreviation: 'FL'],
        [name: 'GEORGIA', abbreviation: 'GA'],
        [name: 'GUAM', abbreviation: 'GU'],
        [name: 'HAWAII', abbreviation: 'HI'],
        [name: 'IDAHO', abbreviation: 'ID'],
        [name: 'ILLINOIS', abbreviation: 'IL'],
        [name: 'INDIANA', abbreviation: 'IN'],
        [name: 'IOWA', abbreviation: 'IA'],
        [name: 'KANSAS', abbreviation: 'KS'],
        [name: 'KENTUCKY', abbreviation: 'KY'],
        [name: 'LOUISIANA', abbreviation: 'LA'],
        [name: 'MAINE', abbreviation: 'ME'],
        [name: 'MARYLAND', abbreviation: 'MD'],
        [name: 'MASSACHUSETTS', abbreviation: 'MA'],
        [name: 'MICHIGAN', abbreviation: 'MI'],
        [name: 'MINNESOTA', abbreviation: 'MN'],
        [name: 'MISSISSIPPI', abbreviation: 'MS'],
        [name: 'MISSOURI', abbreviation: 'MO'],
        [name: 'MONTANA', abbreviation: 'MT'],
        [name: 'NEBRASKA', abbreviation: 'NE'],
        [name: 'NEVADA', abbreviation: 'NV'],
        [name: 'NEW HAMPSHIRE', abbreviation: 'NH'],
        [name: 'NEW JERSEY', abbreviation: 'NJ'],
        [name: 'NEW MEXICO', abbreviation: 'NM'],
        [name: 'NEW YORK', abbreviation: 'NY'],
        [name: 'NORTH CAROLINA', abbreviation: 'NC'],
        [name: 'NORTH DAKOTA', abbreviation: 'ND'],
        [name: 'OHIO', abbreviation: 'OH'],
        [name: 'OKLAHOMA', abbreviation: 'OK'],
        [name: 'OREGON', abbreviation: 'OR'],
        [name: 'PENNSYLVANIA', abbreviation: 'PA'],
        [name: 'PUERTO RICO', abbreviation: 'PR'],
        [name: 'RHODE ISLAND', abbreviation: 'RI'],
        [name: 'SOUTH CAROLINA', abbreviation: 'SC'],
        [name: 'SOUTH DAKOTA', abbreviation: 'SD'],
        [name: 'TENNESSEE', abbreviation: 'TN'],
        [name: 'TEXAS', abbreviation: 'TX'],
        [name: 'UTAH', abbreviation: 'UT'],
        [name: 'VERMONT', abbreviation: 'VT'],
        [name: 'VIRGIN ISLANDS', abbreviation: 'VI'],
        [name: 'VIRGINIA', abbreviation: 'VA'],
        [name: 'WASHINGTON', abbreviation: 'WA'],
        [name: 'WEST VIRGINIA', abbreviation: 'WV'],
        [name: 'WISCONSIN', abbreviation: 'WI'],
        [name: 'WYOMING', abbreviation: 'WY']
    ]
  }
}
