package com.example.b15recyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var button: Button

    private lateinit var adaptador: Adaptador
    private lateinit var paises: MutableList<Countries>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ids y tal
        recyclerView = findViewById(R.id.recyclerView)
        editText = findViewById(R.id.etName)
        button = findViewById(R.id.btn)

        paises = Countries.nameList

        adaptador = Adaptador(paises){ position ->
            paises.removeAt(position)
            adaptador.notifyItemRemoved(position)
        }

        //explicar
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptador

        button.setOnClickListener {
            val inputNombre = editText.text.toString().trim()

            if(inputNombre.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    val nuevoPais = obtenerPaisDeApi(inputNombre)

                    withContext(Dispatchers.Main){
                        paises.add(nuevoPais)
                        adaptador.notifyItemInserted(paises.size-1)
                        editText.text.clear()
                        recyclerView.smoothScrollToPosition(paises.size-1)
                    }
                }
            }
        }
    }

    //le he pedido esta funcion a Geraldo Gutierrez Garrido, porque es un poco EXTRA (sirve para buscar en la api de countries)
    private fun obtenerPaisDeApi(nombreBusqueda: String): Countries {
        return try {
            // 1. Codificar la búsqueda para admitir tildes, 'ñ' y espacios (ej: "México", "Corea del Sur")
            val nombreCodificado = URLEncoder.encode(nombreBusqueda, "UTF-8")

            // 2. Intentar buscar por traducción (/translation/). Si falla, reintentar por nombre (/name/)
            val respuestaJson = try {
                URL("https://restcountries.com/v3.1/translation/$nombreCodificado").readText()
            } catch (e: Exception) {
                URL("https://restcountries.com/v3.1/name/$nombreCodificado").readText()
            }

            val jsonArray = JSONArray(respuestaJson)
            val primerResultado = jsonArray.getJSONObject(0)

            // Extraer la capital
            val arrayCapitales = primerResultado.optJSONArray("capital")
            val capital = if (arrayCapitales != null && arrayCapitales.length() > 0) {
                arrayCapitales.getString(0)
            } else {
                "Desconocida"
            }

            // Extraer el código ISO de 2 letras (cca2) para la bandera (ej: "es", "de", "jp")
            val codigoIso = primerResultado.optString("cca2", "default").lowercase()

            // Extraer el nombre traducido al español si está disponible en el JSON
            val translations = primerResultado.optJSONObject("translations")
            val nombreEspanol = translations?.optJSONObject("spa")?.optString("common")
                ?: primerResultado.getJSONObject("name").optString("common", nombreBusqueda)

            Countries(
                nombre = nombreEspanol,
                id = codigoIso,
                capital = capital
            )
        } catch (e: Exception) {
            // Si el país no existe o no hay conexión
            Countries(
                nombre = nombreBusqueda.replaceFirstChar { it.uppercase() },
                id = "default",
                capital = "Desconocida"
            )
        }
    }

    // Helper HTTP con User-Agent y timeouts configurados
    private fun realizarPeticionHttp(urlString: String): String {
        val url = URL(urlString)
        val conexion = url.openConnection() as HttpURLConnection
        return try {
            conexion.requestMethod = "GET"
            conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Android Mobile App)")
            conexion.connectTimeout = 5000
            conexion.readTimeout = 5000

            val responseCode = conexion.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                conexion.inputStream.bufferedReader().use { it.readText() }
            } else {
                throw Exception("Respuesta HTTP no válida: $responseCode")
            }
        } finally {
            conexion.disconnect()
        }
    }
    // funciona MAL (jamás la pidas código a la Inteligencia Artificial)
}