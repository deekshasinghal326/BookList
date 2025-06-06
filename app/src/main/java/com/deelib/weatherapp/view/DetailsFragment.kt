package com.deelib.weatherapp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.deelib.weatherapp.model.Item
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsFragment : BottomSheetDialogFragment() {
    var selectedBook: Item? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedBook = it.getParcelable(ARG_BOOK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
//                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
                selectedBook?.let { BookDetails(it, onDismiss = { dismiss() }) }

//                }
            }
        }
    }

    companion object {
        const val ARG_BOOK = "book"
        fun newInstance(book: Item): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_BOOK, book)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("UseKtx")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            (this as BottomSheetDialog)?.behavior?.apply {
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    @Composable
    fun BookDetails(item: Item, onDismiss: () -> Unit) {
        Box(
            modifier = Modifier
                .background(Color(0xFF250001)) // Dark red background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopIcons(onDismiss)

                AsyncImage(
                    model = item.volumeInfo.imageLinks.thumbnail.replace("http://", "https://"),
                    contentDescription = "book image",
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 32.dp)
                )

                Text(
                    text = item.volumeInfo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = item.volumeInfo.description ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 3.dp, start = 16.dp, end = 16.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.volumeInfo.authors.firstOrNull() ?: "",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))


                BottomActionBar(item)
            }
        }
    }


    @Composable
    fun TopIcons(onDismiss: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color(0x4DFFFFFF),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onDismiss() }
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color(0x4DFFFFFF)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color(0x4DFFFFFF),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }

    @Composable
    fun BottomActionBar(item: Item) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 20.dp
                ), // Padding from the bottom of the sheet
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x26FFFFFF) // Semi-transparent white as seen in images
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Inner padding for content within the Card
            ) {
                // "BOOK" text and Info Icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "BOOK",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.Info, // The info icon
                        contentDescription = "Book information",
                        tint = Color(0x4DFFFFFF),
                        modifier = Modifier
                            .size(25.dp)
                            .padding(start = 5.dp)
                    )
                }

                // Page Count
                Text(
                    text = item.volumeInfo.pageCount?.toString() ?: "",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Buttons: Sample and Get
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), // Padding above the buttons within the card
                    horizontalArrangement = Arrangement.SpaceBetween, // Distributes space between children
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Sample Button
                    Box(
                        modifier = Modifier
                            .weight(1f) // Make Sample button take equal space
                            .padding(end = 8.dp) // Space between Sample and Get buttons
                            .background(
                                Color(0xFFE0E0E0), // Light gray background for Sample
                                shape = RoundedCornerShape(6.dp)
                            )
                            .border(
                                1.dp,
                                Color(0xFFC0C0C0), // Subtle border color
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable { /* handle sample click */ }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Sample book",
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Sample",
                                color = Color.Black,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 6.dp)
                            )
                        }
                    }

                    // Get Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .background(Color.White, shape = RoundedCornerShape(6.dp))
                            .clickable { /* handle get click */ }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Get",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

}