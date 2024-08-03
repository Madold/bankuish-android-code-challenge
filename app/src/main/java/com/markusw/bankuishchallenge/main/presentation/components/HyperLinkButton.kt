package com.markusw.bankuishchallenge.main.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markusw.bankuishchallenge.R

@Composable
fun HyperLinkButton(
    label: String,
    link: String,
    modifier: Modifier = Modifier
) {

    val uriHandler = LocalUriHandler.current

    Button(
        modifier = modifier,
        onClick = {
            uriHandler.openUri(link)
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Blue
        )
    ) {
        Text(text = label, textDecoration = TextDecoration.Underline)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_link),
            contentDescription = null
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HyperLinkButtonPreview(modifier: Modifier = Modifier) {
    HyperLinkButton(
        label = "Visit Homepage",
        link = "https://google.com"
    )
}