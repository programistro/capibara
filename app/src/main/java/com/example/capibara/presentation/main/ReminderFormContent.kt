package com.example.capibara.presentation.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.presentation.common.GreenButton
import com.example.capibara.presentation.common.UnderlineTextField
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderFormContent(
    state: ReminderFormUiState,
    onTitleChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onPeriodicityChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var periodicityExpanded by remember { mutableStateOf(false) }

    val dateDialog = remember {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day -> onDateChange("$day.${month + 1}.$year") },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    val timeDialog = remember {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeChange(String.format("%02d:%02d", hour, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Напоминание",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Название",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        UnderlineTextField(
            value = state.title,
            onValueChange = onTitleChange,
            placeholder = "Введите название"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Дата начала",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                UnderlineTextField(
                    value = state.date,
                    onValueChange = {},
                    placeholder = "14.9.2026",
                    readOnly = true,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { dateDialog.show() }
                    )
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Время приема",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                UnderlineTextField(
                    value = state.time,
                    onValueChange = {},
                    placeholder = "23:53",
                    readOnly = true,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { timeDialog.show() }
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Периодичность",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        ExposedDropdownMenuBox(
            expanded = periodicityExpanded,
            onExpandedChange = { periodicityExpanded = it }
        ) {
            UnderlineTextField(
                value = state.periodicity,
                onValueChange = {},
                placeholder = "Ежедневно",
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = periodicityExpanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = periodicityExpanded,
                onDismissRequest = { periodicityExpanded = false }
            ) {
                Periodicity.all.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onPeriodicityChange(option)
                            periodicityExpanded = false
                        }
                    )
                }
            }
        }
        state.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        GreenButton(
            text = "Сохранить",
            onClick = onSaveClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        GreenButton(
            text = "Отмена",
            onClick = onClose
        )
    }
}
