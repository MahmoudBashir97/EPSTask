package com.mahmoudbashir.epstask.fragments

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.pojo.DataModel
import com.mahmoudbashir.epstask.ui.MainActivity
import com.mahmoudbashir.epstask.utils.Constants
import com.mahmoudbashir.epstask.viewModel.StViewModel
import java.io.*



class MainScreen_Fragment : Fragment() ,NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawerMain: DrawerLayout
    private lateinit var show_menu:ImageView
    lateinit var capturedImg:ImageView
    lateinit var navView: NavigationView
    lateinit var edtTitle:EditText
    lateinit var edtDesc:EditText
    lateinit var saveBtn:Button
    private lateinit var linPicPhoto:LinearLayout
    lateinit var viewModel:StViewModel

    var photoUri:String?=null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val v =  inflater.inflate(R.layout.main_drawer_layout, container, false)

        // do Initialization for all views
        doInitialization(v)
        //to show(open) drawer
        show_menu.setOnClickListener {
            drawerMain.open()
        }
        //to capture a new image using camera
        linPicPhoto.setOnClickListener {

            openCam()
        }
        //using navigationView for selecting an items in it for doing some actions
        navView.setNavigationItemSelectedListener(this)


        //Save Data to local database (Roomdb)
        saveBtn.setOnClickListener {
           insertingDataToRoom()
        }

        return v
    }

    private fun openCam() {
        //Request permission first
        if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    activity as MainActivity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
            )
        } else {
        val photoIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, Constants.CAPTURE_IMAGE_REQUEST);
        }

    }

    private fun insertingDataToRoom() {
        if (isValidate()){
            Log.d("successMessage : ", "successfuly!")
            val dModel = DataModel(imgUri = photoUri!!, title = edtTitle.text.toString(), description = edtDesc.text.toString())
            val check = viewModel.insertData(dModel).isActive.and(isAdded)
            Log.d("dataAdded: ", "$check")
            if (check){
                photoUri=null
                edtTitle.text.clear()
                edtDesc.text.clear()
                capturedImg.setImageResource(R.drawable.ic_baseline_camera_alt_24)
                displayMessage(requireContext(), "Data has been added successfully.")
            }else{
                displayMessage(requireContext(), "Something Wrong happen!!")
            }
        }
    }

    private fun doInitialization(v: View) {
        drawerMain = v.findViewById(R.id.drawer_layout)
        show_menu = v.findViewById(R.id.show_menu)
        navView = v.findViewById(R.id.nav_view)
        linPicPhoto = v.findViewById(R.id.lin_pic_photo)
        capturedImg = v.findViewById(R.id.img_captured)
        edtTitle = v.findViewById(R.id.edt_title)
        edtDesc = v.findViewById(R.id.edt_desc)
        saveBtn = v.findViewById(R.id.btn_save)

        //initialize ViewModel
        viewModel = (activity as MainActivity).viewModel

    }
    private fun isValidate():Boolean{
        when{
            photoUri == null ->{
                displayMessage(requireContext(), "please capture a new Image!")
                return false
            }
            TextUtils.isEmpty(edtTitle.text) ->{
                edtTitle.error = "please enter a valid input!"
                edtTitle.requestFocus()
                return false
            }
            TextUtils.isEmpty(edtDesc.text)->{
                edtDesc.error = "please enter a valid input!"
                edtDesc.requestFocus()
                return false
            }
            else-> return true
        }
    }

    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun getImageUri(applicationContext: Context, photo: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context?.contentResolver, photo, "Title", null)
        return Uri.parse(path)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode === RESULT_OK) {
                val photo:Bitmap = data?.extras?.get("data") as Bitmap
                val tempUri = getImageUri(requireContext(), photo)
                photoUri = "$tempUri"
                capturedImg.setImageBitmap(photo)

            } else if (resultCode === RESULT_CANCELED) {
                Toast.makeText(requireContext(), "CANCELED ", Toast.LENGTH_LONG).show()
            }
        }

}

override fun onNavigationItemSelected(item: MenuItem): Boolean {
when (item.itemId){
    R.id.savedData_Fragment -> {
        findNavController().navigate(MainScreen_FragmentDirections.actionMainScreenFragmentToSavedDataFragment())
    }
    R.id.viewPagerImages_Fragment -> {
        findNavController().navigate(MainScreen_FragmentDirections.actionMainScreenFragmentToViewPagerImagesFragment())
    }
    R.id.apiData_Fragment -> {
        findNavController().navigate(MainScreen_FragmentDirections.actionMainScreenFragmentToApiDataFragment())
    }
    else ->{
        drawerMain.close()
    }
}
drawerMain.closeDrawer(GravityCompat.START)
return true
}
}