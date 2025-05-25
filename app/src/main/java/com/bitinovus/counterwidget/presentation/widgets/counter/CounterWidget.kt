package com.bitinovus.counterwidget.presentation.widgets.counter

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.ButtonDefaults
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle

// Example 1
val COUNT_KEY = intPreferencesKey("count")

class CounterWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val count = prefs[COUNT_KEY] ?: 0
            Counter(count = count)
        }
    }

    @Composable
    private fun Counter(count: Int) {
        Column(
            modifier = GlanceModifier
                .appWidgetBackground()
                .background(color = Color(0xFFFFFFFF))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    text = "+", onClick = actionRunCallback<Increment>(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GlanceTheme.colors.background,
                        contentColor = GlanceTheme.colors.primary
                    ),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "$count",
                    modifier = GlanceModifier
                        .padding(horizontal = 30.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Button(
                    text = "-", onClick = actionRunCallback<Decrement>(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GlanceTheme.colors.background,
                        contentColor = GlanceTheme.colors.primary
                    ),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
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
            val currentCount = prefs[COUNT_KEY] ?: 0
            prefs[COUNT_KEY] = currentCount + 1
        }
        CounterWidget().update(context, glanceId)
    }
}

class Decrement : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[COUNT_KEY] ?: 0
            if (currentCount > 0) {
                prefs[COUNT_KEY] = currentCount - 1
            } else {
                prefs[COUNT_KEY] = 0
            }
        }
        CounterWidget().update(context, glanceId)
    }
}

