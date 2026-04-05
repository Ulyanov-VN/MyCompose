package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface {
                    ContactDetails(
                        contact = Contact(
                            name = "Евгений",
                            surname = "Андреевич",
                            familyName = "Лукашин",
                            imageRes = null,
                            isFavorite = true,
                            phone = "+7 495 495 95 95",
                            address = "г. Москва, 3-я улица Строителей, д. 25,кв. 12",
                            email = "Elukashin@practicum.ru"
                        )
                    )
                }
            }
        }
    }
}