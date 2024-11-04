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
import com.gdbc.clubdeportivo.ui.abonar.AbonarFragment
import com.gdbc.clubdeportivo.ui.adapter.MorosoAdapter

class ListarCuotasVencidasFragment : Fragment() {

    private var _binding: FragmentListarCuotasVencidasBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var morosoAdapter: MorosoAdapter
    private lateinit var dbHelper: BDatos
    private lateinit var morosoRepository: MorosoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListarCuotasVencidasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initDB()
        initUI()
        initListeners()

        return root
    }

    private fun initDB() {
        dbHelper = BDatos(requireContext())
        morosoRepository = MorosoRepository(dbHelper)
    }

    private fun initUI() {
        val searchView = binding.srcDefaulter
        val plate = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
        plate.setBackgroundColor(android.graphics.Color.TRANSPARENT)
        binding.srcDefaulter.queryHint = "Buscar por nombre..."
    }

    private fun initListeners() {

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

    private fun refreshData() {
        val morosos = morosoRepository.listarMorosos()
        recyclerView = binding.recyclerViewDefaulter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (morosos.isEmpty()) {
            binding.ivNoMorososImage.visibility = View.VISIBLE
            binding.tvNoMorosos.visibility = View.VISIBLE
            binding.recyclerViewDefaulter.visibility = View.GONE
        } else {
            binding.ivNoMorososImage.visibility = View.GONE
            binding.tvNoMorosos.visibility = View.GONE
            binding.recyclerViewDefaulter.visibility = View.VISIBLE

            morosoAdapter = MorosoAdapter(morosos) { dni ->
                val bundle = Bundle().apply {
                    putString(AbonarFragment.DNI, dni)
                }
                findNavController().navigate(R.id.nav_abonar, bundle)
            }
            recyclerView.adapter = morosoAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dbHelper.close()
        _binding = null

    }
}