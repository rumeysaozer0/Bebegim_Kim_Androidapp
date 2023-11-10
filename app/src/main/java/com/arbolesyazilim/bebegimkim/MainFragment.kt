package com.arbolesyazilim.bebegimkim

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arbolesyazilim.bebegimkim.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val PERMISSION_REQUEST_CODE = 1
    private val PICK_IMAGE_REQUEST_CODE = 2
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var lastClickedViewId: Int = -1
    private var isWomanImageSelected = false
    private var isManImageSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.womanImage.setOnClickListener {
            lastClickedViewId = it.id
            context?.let { context ->
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            }
        }

        binding.manImage.setOnClickListener {
            lastClickedViewId = it.id
            context?.let { context ->
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    openGallery()
                }
            }
        }

        binding.button.setOnClickListener {
            if (!isWomanImageSelected || !isManImageSelected) {
                Toast.makeText(
                    context,
                    "Lütfen her iki resmi de seçin.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action = MainFragmentDirections.actionMainFragmentToBabyFragment2()
                findNavController().navigate(action)
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(
                        context,
                        "Galeri erişim izni reddedildi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!

            // Hangi CircleImageView'e eklemek istiyorsanız onu seçin
            val selectedImageView =
                if (lastClickedViewId == R.id.womanImage) {
                    binding.womanImage
                } else if (lastClickedViewId == R.id.manImage) {
                    binding.manImage
                } else {
                    null
                }

            // Seçilen resmi CircleImageView'e yükle
            selectedImageView?.setImageURI(selectedImageUri)

            // Hangi resim seçildiyse ona göre durumu güncelle
            when (lastClickedViewId) {
                R.id.womanImage -> isWomanImageSelected = true
                R.id.manImage -> isManImageSelected = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
