package com.example.appdog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdog.databinding.FragmentImagesBinding
import com.example.appdog.viewmodel.DogViewModel


class ImagesFragment : Fragment() {

    private lateinit var binding : FragmentImagesBinding
    private val viewModel : DogViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breed = arguments?.getString("breed") ?: ""
        val adapter = ImagesAdapter()
        binding.tbImages.setTitle("Imagenes de razas: $breed")
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 1)
        viewModel.getImagesByBreedFromInternet(breed)
        viewModel.getImages().observe(viewLifecycleOwner) {
            it?.let {adapter.update(it)  }
        }

        adapter.selectedImage().observe(viewLifecycleOwner) { image ->
            image?.let {
                val confirmationMessage = if (image.fav) {
                    "¿Estás seguro de que quieres eliminar esta imagen de tus favoritos?"
                } else {
                    "¿Estás seguro de que quieres marcar esta imagen como favorita?"
                }

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Confirmación")
                builder.setMessage(confirmationMessage)
                builder.setIcon(R.drawable.ic_question)
                builder.setPositiveButton("Sí") { dialog, _ ->
                    image.fav = !image.fav
                    viewModel.updateFav(image)
                    dialog.dismiss()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }


        binding.fabBack.setOnClickListener { parentFragmentManager.popBackStack() }

    }


}