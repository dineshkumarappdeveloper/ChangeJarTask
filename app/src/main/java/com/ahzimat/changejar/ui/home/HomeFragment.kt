package com.ahzimat.changejar.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ahzimat.changejar.R
import com.ahzimat.changejar.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var buttonIds = arrayListOf<Int>()
    var componentPositions = arrayListOf<Rectangle>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.buttonUndo.setOnClickListener {
            if(buttonIds.isNotEmpty()){
                val lastInsertedId = buttonIds.last()
                binding.linearLayout.removeView(root.findViewById(lastInsertedId))
                buttonIds.removeAt((buttonIds.size)-1)
            }else{
                Toast.makeText(requireContext(),"No views available", Toast.LENGTH_SHORT).show()
            }

        }



        binding.buttonUndo.setOnLongClickListener {
            if(buttonIds.isNotEmpty()){
                binding.linearLayout.removeAllViews()
                buttonIds.clear()
            }else{
                Toast.makeText(requireContext(),"No views available", Toast.LENGTH_SHORT).show()
            }
            return@setOnLongClickListener true
        }

        binding.buttonRound.setOnClickListener {
            addNewButton(false)
        }
        binding.buttonSquare.setOnClickListener {
            addNewButton(true)
        }


        return root
    }


    private fun addNewButton(isSquare:Boolean) {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            val componentWidth = 140
            val componentHeight = 140


            binding.linearLayout.post {
                val Ra = Random()
                val dx = Ra.nextFloat() * (binding.linearLayout.width - componentWidth)
                val dy = Ra.nextFloat() * (binding.linearLayout.height - componentHeight)

                var centerPointOfX =  dx+(componentWidth/2)
                var centerPointOfY =  dy+(componentHeight/2)




                val newId = ((0..100).random())
                buttonIds.add(newId)




                val btnTag = Button(requireContext())
                btnTag.layoutParams = LinearLayout.LayoutParams( componentWidth, componentHeight )

                if(!isSquare){
                    btnTag.background = ContextCompat.getDrawable(requireContext(),R.drawable.round_button )
                }
                btnTag.text = "${newId}"
                btnTag.id = newId// + ((0..100).random())
                btnTag.animate().x(dx).y(dy).setDuration(0).start()
                binding.linearLayout.addView(btnTag)


             //   binding.textViewUpdatedDisplay.text = "${rectangle.centerX} ${centerPointOfX}  ${btnTag.id} Button position X:$dx Y:$dy"

            }


        },1)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}