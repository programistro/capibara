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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capibara.domain.model.Periodicity
import com.example.capibara.presentation.common.UnderlineTextField
import com.example.capibara.ui.theme.IconDark
import com.example.capibara.ui.theme.PrimaryGreen
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderFormContent(
    state: ReminderFormUiState,
    onTitleChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onPeriodicityChange: (String) -> Unit,
    onNotifyEnabledChange: (Boolean) -> Unit,
    onNotifyMinutesChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var periodicityExpanded by remember { mutableStateOf(false) }

    val dateDialog = remember {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, day ->
                onDateChange(String.format(Locale.getDefault(), "%02d.%02d.%d", day, month + 1, year))
            },
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
            .verticalScroll(rememberScrollState())
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
                    enabled = false,
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
                    enabled = false,
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Напоминание",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = state.notifyEnabled,
                onCheckedChange = onNotifyEnabledChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = IconDark,
                    checkedTrackColor = PrimaryGreen
                )
            )
            UnderlineTextField(
                value = state.notifyMinutes,
                onValueChange = onNotifyMinutesChange,
                placeholder = "Минуты",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "* По умолчанию напоминание за 3 часа (180 минут)",
            fontSize = 13.sp,
            color = Color.DarkGray,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FormButton(
                text = "Отмена",
                onClick = onClose,
                modifier = Modifier.weight(1f)
            )
            FormButton(
                text = "Сохранить",
                onClick = onSaveClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FormButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
            contentColor = IconDark
        ),
        modifier = modifier.height(56.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}
