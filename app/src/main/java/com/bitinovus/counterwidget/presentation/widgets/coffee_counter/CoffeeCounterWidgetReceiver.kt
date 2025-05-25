package com.bitinovus.counterwidget.presentation.widgets.coffee_counter

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

// Coffee Cup counter Receiver
class CoffeeCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CoffeeCounterWidget()
}