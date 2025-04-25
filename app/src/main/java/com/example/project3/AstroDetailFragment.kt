package com.example.project3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.project3.databinding.FragmentAstroDetailBinding
import java.text.DateFormat

private const val TAG = "AstroDetailFragment"
class AstroDetailFragment: Fragment() {
    private lateinit var binding: FragmentAstroDetailBinding
    private val args: AstroDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "The astro title is : ${args.title}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentAstroDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image.setImageResource(R.drawable.placeholder_image)
        var date = args.date
        //DateFormat.getDateInstance(DateFormat.FULL).format(date)
        binding.title.setText(args.title)
        binding.date.setText("Photo For $date")
        binding.description.setText(args.description)
        if (args.mediaType == "image"){
            binding.image.load(args.url)
        } else if (args.mediaType == "video"){
            binding.image.setImageResource(R.drawable.placeholder_image)
            binding.image.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.url))
                startActivity(intent)
            }
        }

    }

}