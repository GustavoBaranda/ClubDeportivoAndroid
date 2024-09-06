package com.gdbc.clubdeportivo.ui.listarcuotasvencidas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
import com.gdbc.clubdeportivo.databinding.FragmentListarCuotasVencidasBinding

class ListarCuotasVencidasFragment : Fragment() {
    private var _binding: FragmentListarCuotasVencidasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val ListarCuotasVencidasViewModel =
            ViewModelProvider(this).get(ListarCuotasVencidasViewModel::class.java)*/

        _binding = FragmentListarCuotasVencidasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
