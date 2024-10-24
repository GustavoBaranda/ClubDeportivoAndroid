package com.gdbc.clubdeportivo.ui.listarcuotasvencidas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gdbc.clubdeportivo.R
import com.gdbc.clubdeportivo.data.database.BDatos
import com.gdbc.clubdeportivo.data.repository.MorosoRepository
import com.gdbc.clubdeportivo.databinding.FragmentListarCuotasVencidasBinding
import com.gdbc.clubdeportivo.ui.adapter.MorosoAdapter

class ListarCuotasVencidasFragment : Fragment() {

    private var _binding: FragmentListarCuotasVencidasBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var morosoAdapter: MorosoAdapter
    private lateinit var morosoRepository: MorosoRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListarCuotasVencidasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dbHelper = BDatos(requireContext())
        morosoRepository = MorosoRepository(dbHelper)
        initUI()



        return root
    }

    private fun initUI() {

        val searchView = binding.srcDefaulter
        val plate = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
        plate.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        binding.srcDefaulter.queryHint = "Buscar por nombre..."


        binding.srcDefaulter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                morosoAdapter.filter(newText)

                if (newText.isNullOrEmpty()) {
                    binding.srcDefaulter.setQueryHint("Buscar por nombre...")
                } else {
                    binding.srcDefaulter.setQueryHint("")
                }
                return true
            }
        })

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.nav_home)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        val morosos = morosoRepository.listarMorosos()
        recyclerView = binding.recyclerViewDefaulter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        morosoAdapter = MorosoAdapter(morosos) { idCliente, dni ->
            val bundle = Bundle().apply {
                putString("dni", dni)
                putInt("idCliente", idCliente)
            }
            findNavController().navigate(R.id.nav_abonar, bundle)
        }
        recyclerView.adapter = morosoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}