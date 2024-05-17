package com.example.widgettest.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.widgettest.data.CoinNameAndSymbol
import com.example.widgettest.data.CoinNameRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinPicker(coinNameRepo: CoinNameRepo, onCoinPicked: (coin: CoinNameAndSymbol) -> Unit) {
    var coinToAdd by remember {
        mutableStateOf("")
    }

    val addCoin = {
        val coin = coinNameRepo.getExact(coinToAdd) ?: coinToAdd.uppercase().let {
            CoinNameAndSymbol(it, it)
        }
        onCoinPicked(coin)
        coinToAdd = ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(1f),
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                value = coinToAdd,
                onValueChange = {
                    coinToAdd = it
                    if (!expanded && coinToAdd.isNotBlank()) {
                        expanded = true
                    }
                },
                label = { Text("Coin symbol") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    autoCorrect = false
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addCoin()
                        this.defaultKeyboardAction(ImeAction.Done)
                    }
                )
            )
            // filter options based on text field value
            val filteringOptions = coinNameRepo.getMatches(coinToAdd)
            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.display()) },
                            onClick = {
                                coinToAdd = selectionOption.symbol
                                expanded = false
                                addCoin()
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        SmallFloatingActionButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = addCoin
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add coin",
            )
        }
    }
}