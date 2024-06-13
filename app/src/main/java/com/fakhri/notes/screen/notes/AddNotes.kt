package com.fakhri.notes.screen.notes

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fakhri.notes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotes(
    inputTitle: MutableState<String>,
    inputBody: MutableState<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(dimensionResource(R.dimen.medium))) {
        TextField(
            value = inputTitle.value,
            onValueChange = {
                inputTitle.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,

                ),
            textStyle = MaterialTheme.typography.displayMedium,
            placeholder = {
                Text(
                    text = "Masukkan judul",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        )

        TextField(
            value = inputBody.value,
            onValueChange = {
                inputBody.value = it
            }, colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = {
                Text(
                    text = "Masukkan isi",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}