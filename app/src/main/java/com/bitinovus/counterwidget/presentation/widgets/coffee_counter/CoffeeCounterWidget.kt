package com.bitinovus.counterwidget.presentation.widgets.coffee_counter

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.bitinovus.counterwidget.R
import com.bitinovus.counterwidget.presentation.widgets.counter.CounterWidget

val COFFEE_CUP_COUNTER = intPreferencesKey("coffee_cup")

// Example 2
// Coffee Cup
class CoffeeCounterWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent {
            val prefs = currentState<Preferences>()
            val cupsCount = prefs[COFFEE_CUP_COUNTER] ?: 0
            CofferCupCounter(cupsNumber = cupsCount)
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun CofferCupCounter(cupsNumber: Int) {
        Column(
            modifier = GlanceModifier
                .appWidgetBackground()
                .background(R.color.primary_yellow_25)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$cupsNumber",
                style = TextStyle(
                    color = ColorProvider(R.color.primary_brown_25),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            when (cupsNumber) {
                1 -> {
                    Image(
                        modifier = GlanceModifier
                            .padding(vertical = 10.dp),
                        provider = ImageProvider(R.drawable.battery_2),
                        contentDescription = "cup 1",
                        colorFilter = ColorFilter.tint(
                            colorProvider = ColorProvider(R.color.primary_green_25)
                        )
                    )
                }

                2 -> {
                    Image(
                        modifier = GlanceModifier
                            .padding(vertical = 10.dp),
                        provider = ImageProvider(R.drawable.battery_4),
                        contentDescription = "cup 2",
                        colorFilter = ColorFilter.tint(
                            colorProvider = ColorProvider(R.color.primary_green_25)
                        )
                    )
                }

                3 -> {
                    Image(
                        modifier = GlanceModifier
                            .padding(vertical = 10.dp),
                        provider = ImageProvider(R.drawable.battery_6),
                        contentDescription = "cup 3",
                        colorFilter = ColorFilter.tint(
                            colorProvider = ColorProvider(R.color.primary_green_25)
                        ),
                    )
                }

                4 -> {
                    Column(
                        modifier = GlanceModifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            modifier = GlanceModifier
                                .padding(vertical = 10.dp),
                            provider = ImageProvider(R.drawable.battery_full),
                            contentDescription = "cup 4",
                            colorFilter = ColorFilter.tint(
                                colorProvider = ColorProvider(R.color.primary_green_25)
                            )
                        )
                        Text(
                            modifier = GlanceModifier.padding(bottom = 10.dp),
                            text = "Full",
                            style = TextStyle(
                                color = ColorProvider(R.color.primary_green_25),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                }
            }
            Column(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (cupsNumber != 4) {
                    CircleIconButton(
                        // enabled = false, this option did NOT work
                        contentDescription = null,
                        imageProvider = ImageProvider(R.drawable.coffee_cup),
                        contentColor = ColorProvider(R.color.primary_brown_25),
                        backgroundColor = ColorProvider(R.color.primary_yellow_45),
                        onClick = actionRunCallback<Increment>()
                    )
                } else {
                    CircleIconButton(
                        contentDescription = null,
                        imageProvider = ImageProvider(R.drawable.coffee_cup),
                        contentColor = ColorProvider(R.color.primary_brown_25),
                        backgroundColor = ColorProvider(R.color.primary_yellow_45),
                        onClick = {}
                    )
                }

                if (cupsNumber > 0) {
                    Spacer(modifier = GlanceModifier.padding(5.dp))
                    Button(
                        text = "Reset",
                        onClick = actionRunCallback<ResetCounter>(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorProvider(R.color.primary_yellow_45),
                            contentColor = ColorProvider(R.color.primary_brown_25)
                        ),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }

        }
    }
}

class Increment : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[COFFEE_CUP_COUNTER] ?: 0
            prefs[COFFEE_CUP_COUNTER] = currentCount + 1
        }
        CounterWidget().update(context, glanceId)
    }
}

class ResetCounter : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[COFFEE_CUP_COUNTER] ?: 0
            if (currentCount > 0) {
                prefs[COFFEE_CUP_COUNTER] = 0
            }
        }
        CounterWidget().update(context, glanceId)
    }
}
