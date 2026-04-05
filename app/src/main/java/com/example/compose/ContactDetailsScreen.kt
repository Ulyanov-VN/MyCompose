package com.example.compose

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.compose.ui.text.font.FontStyle
import androidx.appcompat.widget.AppCompatImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.viewinterop.AndroidView

data class Contact(
    val name: String, // Имя!
    val surname: String? = null, // Отчество
    val familyName: String, // Фамилия
    val imageRes: Int? = null, // Ресурс фотографии
    val isFavorite: Boolean = false, // Признак избранного контакта
    val phone: String, // Телефон
    val address: String, // Адрес
    val email: String? = null, // E-mail
)

@Composable
fun ContactDetails(
    contact: Contact,
    modifier: Modifier = Modifier
) {
    val firstLineName = buildString {
        append(contact.name)

        if (!contact.surname.isNullOrBlank()) {
            append(" ")
            append(contact.surname)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactAvatar(
                name = contact.name,
                familyName = contact.familyName,
                imageRes = contact.imageRes
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = firstLineName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contact.familyName,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                if (contact.isFavorite) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.star_big_on),
                        contentDescription = "Избранный контакт",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        InfoRow(
            label = stringResource(R.string.phone),
            value = contact.phone
        )

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(
            label = stringResource(R.string.address),
            value = contact.address
        )

        if (!contact.email.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                label = stringResource(R.string.email),
                value = contact.email
            )
        }
    }
}

@Composable
fun ContactAvatar(
    name: String,
    familyName: String,
    @DrawableRes imageRes: Int?,
    modifier: Modifier = Modifier
) {
    val initials = "${name.take(1)}${familyName.take(1)}".uppercase()

    Box(
        modifier = modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageRes != null) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Фотография контакта",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        AppCompatImageView(context).apply {
                            setImageResource(R.drawable.circle)
                            scaleType = ImageView.ScaleType.FIT_XY
                        }
                    },
                    modifier = Modifier.size(80.dp)
                )

                Text(
                    text = initials,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.18f))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.22f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(0.55f)
                .padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFavoriteContactWithoutPhoto() {
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
                    address = "г.Москва,3-я улица Строителей, д. 25,кв. 12",
                    email = "Elukashin@practicum.ru"
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNotFavoriteWithPhotoWithoutEmail() {
    MaterialTheme {
        Surface {
            ContactDetails(
                contact = Contact(
                    name = "Василий",
                    surname = null,
                    familyName = "Кузякин",
                    imageRes = R.drawable.contact_photo,
                    isFavorite = false,
                    phone = "+7 999 123 45 67",
                    address = "Ивановская область, дер.Крутово, д. 4",
                    email = null
                )
            )
        }
    }
}