package org.d3if0031.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if0031.hitungbmi.R
import org.d3if0031.hitungbmi.data.KategoriBmi
import org.d3if0031.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHitungBinding.inflate(
                layoutInflater, container, false)

        binding.button.setOnClickListener { hitungBmi() }
        binding.saranButton.setOnClickListener { view: View ->
            view.run {
                findNavController().navigate(
                        HitungFragmentDirections.
                        actionHitungFragmentToSaranFragment(kategoriBmi)
                )
            }
        }
        binding.shareButton.setOnClickListener { shareData() }
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitungBmi() {

        /* val berat = binding.beratEditText.text.toString().toFloat()
      val tinggi = binding.tinggiEditText.text.toString().toFloat() / 100
      val bmi = berat / (tinggi * tinggi)*/

        val berat = binding.beratEditText.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()

            return
        }
        val tinggiCm = tinggi.toFloat() / 100

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)

        val kategori = getKategori(bmi, isMale)
        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
        binding.saranButton.visibility = View.VISIBLE
        binding.buttonGroup.visibility = View.VISIBLE
    }
    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_template,
                binding.beratEditText.text,
                binding.tinggiEditText.text,
                gender,
                binding.bmiTextView.text,
                binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                        requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }
    fun getKategori(bmi: Float, isMale: Boolean): String {
        kategoriBmi = if (isMale) {
            when {

                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL

            }
        } else {
            when {

                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        val stringRes = when (kategoriBmi) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }

        return getString(stringRes)
    }
}


