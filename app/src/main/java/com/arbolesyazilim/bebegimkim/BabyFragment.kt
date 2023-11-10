package com.arbolesyazilim.bebegimkim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arbolesyazilim.bebegimkim.databinding.FragmentBabyBinding


class BabyFragment : Fragment() {
    private var _binding: FragmentBabyBinding? = null
    private val binding get() = _binding!!
    // Drawable resim isimlerini içeren liste
    private val babyImageNames = arrayOf("bebek1", "bebek2", "bebek3", "bebek4", "bebek5","bebek6","bebek7","bebek8")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBabyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Rastgele bir bebek resmi ismi seç
        val randomBabyImageName = getRandomBabyImageName()

        // Seçilen bebek resmini ImageView'e yükle
        val resourceId = resources.getIdentifier(randomBabyImageName, "drawable", activity?.packageName)
        binding.imageView.setImageResource(resourceId)

    }

    // Bebek resim isimleri listesinden rastgele bir isim seçen fonksiyon
    private fun getRandomBabyImageName(): String {
        return babyImageNames.random()
    }

}


