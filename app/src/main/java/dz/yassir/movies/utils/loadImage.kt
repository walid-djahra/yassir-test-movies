package dz.yassir.movies.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dz.yassir.movies.R
import dz.yassir.movies.utils.StringUtils.Companion.BASE_URL_IMAGE

/**
 * @author Djahra walid , created on 12/03/2023
 */
fun loadImage(path: String?, imageView: ImageView, loadingImage : ProgressBar) {
    Picasso.get()
        .load(BASE_URL_IMAGE + path)
        .error(R.drawable.movie)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .into(imageView, object : Callback {
            override fun onSuccess() {
                loadingImage.visibility= View.GONE
            }

            override fun onError(e: Exception?) {
                Picasso.get()
                    .load(BASE_URL_IMAGE + path)
                    .error(R.drawable.movie)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            loadingImage.visibility= View.GONE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            loadingImage.visibility = View.GONE
                        }
                    })
            }

        })
}