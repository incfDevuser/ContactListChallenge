package com.example.contactlistexample

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private var isFiltered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Capturar los elementos desde el MainActivity.kt
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val cbAvailable = findViewById<CheckBox>(R.id.cbAvailable)
        val btnAddContact = findViewById<Button>(R.id.btnAddContact)
        val btnFilterAvailable = findViewById<Button>(R.id.btnFilterAvailable)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        btnAddContact.setOnClickListener{
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val isAvailable = cbAvailable.isChecked
            if(name.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Ingresa todos los campos!", Toast.LENGTH_SHORT).show()
            }else{
                val contact = Contact(name, phone, isAvailable)
                contactList.add(contact)
                adapter.updateContacts(contactList)
                println("Contacto añadido: $name, Disponible: $isAvailable")
                etName.text.clear()
                etPhone.text.clear()
                cbAvailable.isChecked = false
                Toast.makeText(this, "Contacto agregado", Toast.LENGTH_SHORT).show()
            }
        }
        btnFilterAvailable.setOnClickListener {
            if (!isFiltered) {
                val filteredContacts = contactList.filter { it.isAvailable }
                if (filteredContacts.isEmpty()) {
                    Toast.makeText(this, "No hay contactos disponibles", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.updateContacts(filteredContacts)
                    isFiltered = true
                    btnFilterAvailable.text = "Mostrar todos los contactos"
                }
            } else {
                adapter.updateContacts(contactList)
                isFiltered = false
                btnFilterAvailable.text = "Filtrar disponibles"
            }
        }

    }

    // RecyclerView
    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}