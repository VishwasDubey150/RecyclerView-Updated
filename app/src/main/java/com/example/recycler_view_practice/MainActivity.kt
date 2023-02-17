package com.example.recycler_view_practice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var newAL: ArrayList<list>
    private lateinit var image: Array<Int>
    private lateinit var name: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        image = arrayOf(
            R.drawable.vk,
            R.drawable.srk,
            R.drawable.em,
            R.drawable.zk,
            R.drawable.sm,
            R.drawable.ab)

        name = arrayOf(
            "Virat Kohli",
            "Shah Rukh Khan",
            "Elon Musk",
            "Zakir Khan",
            "Shawn Mendes",
            "Abhishek Bisht")

        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        newAL = arrayListOf<list>()
        getUserdata()
    }

    private fun getUserdata() {
        for (i in image.indices) {
            val Lists = list(image[i], name[i])
            newAL.add(Lists)
        }
        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        var adapter = MyAdapter(newAL)
        rv.adapter = adapter


        var  swipeGesture=object :SwipeGesture(this)
        {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction)
                {
                    ItemTouchHelper.LEFT->{
                        adapter.deleteItem(viewHolder.adapterPosition)
                        Toast.makeText(this@MainActivity,"Deleted",Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT->{
                        val archiveItem=newAL[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addItem(newAL.size,archiveItem)
                        Toast.makeText(this@MainActivity,"Archived",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val touchHelper=ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(rv)



        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity,
                    "You clicked on ${(name[position])}",
                    Toast.LENGTH_SHORT).show()
            }
        })




    }
}