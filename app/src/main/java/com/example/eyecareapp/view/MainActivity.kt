package com.example.eyecareapp.view

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.eyecareapp.R
import com.example.eyecareapp.api.ApiInterface
import com.example.eyecareapp.api.RetrofitHelper
import com.example.eyecareapp.databinding.ActivityMainBinding
import com.example.eyecareapp.model.Attribute
import com.example.eyecareapp.model.Data
import com.example.eyecareapp.repository.JSONRepository
import com.example.eyecareapp.viewmodel.UIViewModel
import com.example.eyecareapp.viewmodel.UIViewModelFactory
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: UIViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val imageList = ArrayList<SlideModel>()
        //val imageList = ArrayList<ImageItem>()

        val apiInterface = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        val repository = JSONRepository(apiInterface)
        viewModel =
            ViewModelProvider(this, UIViewModelFactory(repository)).get(UIViewModel::class.java)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        viewModel.data.observe(this) { jsonData ->
            binding.progressbar.visibility = View.GONE
            binding.pageScrollView.visibility = View.VISIBLE
            Log.d("JSONDATA", jsonData.data.description)

            val data: Data = jsonData.data
            val description = data.description

            val sku = data.sku
            val name = data.name
            val brandName = data.brand_name

            binding.toolbar.title = name

            val listOfConfigurableOptions = data.configurable_option

            var listOfAttributes: List<Attribute>
            val imageUrlList = mutableListOf<String>()
            for (configurableOption in listOfConfigurableOptions) {
                listOfAttributes = configurableOption.attributes

                for (attribute in listOfAttributes) {
                    imageUrlList.add(attribute.swatch_url)
                }

            }

            val sliderImagesUrlList = mutableListOf<String>()
            val images: List<String> = data.images

            for (image in images) {
                sliderImagesUrlList.add(image)
            }

            val scaleType: ScaleTypes = ScaleTypes.FIT
            for (i in sliderImagesUrlList.indices) {
                imageList.add(SlideModel(sliderImagesUrlList[i], scaleType))
            }

            Log.d("SLIDER_IMAGE_LENGTH", imageList.size.toString())


            val imageSliderBinding = binding.imageSlider
            imageSliderBinding.setImageList(imageList)

            val screenHeight = displayMetrics.heightPixels

            val imageHeight = viewModel.getImageHeight(screenHeight)

            val layoutParams = imageSliderBinding.layoutParams
            layoutParams.height = imageHeight
            imageSliderBinding.layoutParams = layoutParams


            Picasso.get().load(imageUrlList[0]).into(binding.eyeType1)
            Picasso.get().load(imageUrlList[1]).into(binding.eyeType2)
            Picasso.get().load(imageUrlList[2]).into(binding.eyeType3)
            Picasso.get().load(imageUrlList[3]).into(binding.eyeType4)
            Picasso.get().load(imageUrlList[4]).into(binding.eyeType5)
            Picasso.get().load(imageUrlList[5]).into(binding.eyeType6)
            Picasso.get().load(imageUrlList[6]).into(binding.eyeType7)
            Picasso.get().load(imageUrlList[7]).into(binding.eyeType8)
            Picasso.get().load(imageUrlList[8]).into(binding.eyeType9)
            Picasso.get().load(imageUrlList[9]).into(binding.eyeType10)


            binding.eyeProblemTitle.text = brandName
            binding.subtitle.text = name
            binding.subtitle22.text = sku

            binding.productInfo.text =
                description.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }




        }


        val shape = binding.shareTxtBtn.background as GradientDrawable
        shape.setStroke(2, getColor(R.color.black))


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.fav_option -> Toast.makeText(this, "Fav option selected", Toast.LENGTH_LONG).show()
            R.id.share_option -> Toast.makeText(this, "Share option selected", Toast.LENGTH_LONG)
                .show()

            R.id.cart_option -> Toast.makeText(this, "Cart option selected", Toast.LENGTH_LONG)
                .show()
        }
        return super.onOptionsItemSelected(item)
    }


}