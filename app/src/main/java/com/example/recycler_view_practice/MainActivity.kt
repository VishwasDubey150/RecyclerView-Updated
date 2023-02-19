package com.example.recycler_view_practice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var newAL: ArrayList<list>
    private lateinit var image: Array<Int>
    private lateinit var name: Array<String>
    private lateinit var dec: Array<String>
    private lateinit var isExpandable: Array<Boolean>
    private lateinit var temparrayList:ArrayList<list>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = arrayOf(
            R.drawable.vk,
            R.drawable.srk,
            R.drawable.em,
            R.drawable.zk,
            R.drawable.sm,
            R.drawable.ab,
            R.drawable.lm,
            R.drawable.cr,
            R.drawable.ms,
            R.drawable.mz,
            R.drawable.rt)

        name = arrayOf(
            "Virat Kohli",
            "Shah Rukh Khan",
            "Elon Musk",
            "Zakir Khan",
            "Shawn Mendes",
            "Abhishek Bisht",
            "lionel Messi",
            "Cristiano Ronaldo",
            "Ms Dhoni",
            "Mark Zuckerberg",
            "Ratan Tata")

        dec= arrayOf(
            "Virat Kohli is an Indian international cricketer and " +
                    "former captain of the Indian national team who " +
                    "plays as a right-handed batsman for Royal Challengers" +
                    " Bangalore in the IPL ",

            "Shah Rukh Khan, also known by the initialism SRK, " +
                    "is an Indian actor and film producer who works" +
                    " in Hindi films. Referred to in the media as the" +
                    " \"Baadshah of Bollywood\" and \"King Khan\"",

            "Elon Reeve Musk FRS is a business magnate and investor." +
                    " He is the founder, CEO and chief engineer of SpaceX; " +
                    "angel investor, CEO and product architect of Tesla," +
                    " Inc.; owner and CEO of Twitter, Inc.",

            "Zakir Khan is an Indian comedian and actor. " +
                    "In 2012, he rose to popularity by winning" +
                    " Comedy Central India's Best Stand Up Comedian competition.",

            "Shawn Peter Raul Mendes is a Canadian pop singer-songwriter. " +
                    "He gained a following in 2013, when he posted song covers " +
                    "on the video sharing platform Vine.",

            "A Youtuber and a software engineer at Meta Dublin",

            "Lionel Andrés Messi, also known as Leo Messi, is an Argentine professional" +
                    " footballer who plays as a forward for Ligue 1 club Paris Saint-Germain " +
                    "and captains the Argentina national team.",

            "Cristiano Ronaldo dos Santos Aveiro GOIH ComM is a Portuguese professional " +
                    "footballer who plays as a forward for and captains both Saudi Professional" +
                    " League club Al Nassr and the Portugal national team.",

            "Mahendra Singh Dhoni, commonly known as MS Dhoni, is a former Indian " +
                    "cricketer and captain of the Indian national team in limited-overs " +
                    "formats from 2007 to 2017, and in Test cricket from 2008 to 2014.",

            "Mark Elliot Zuckerberg is an American business magnate, internet entrepreneur," +
                    " and philanthropist. He is known for co-founding the social media website " +
                    "Facebook and its parent company Meta Platforms,",

            "Ratan Naval Tata is an Indian businessman and former chairman of Tata Sons." +
                    " He was also the chairman of the Tata Group from 1990 to 2012, serving " +
                    "also as interim chairman from October 2016 through February 2017"
        )

        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        newAL = arrayListOf<list>()
        temparrayList= arrayListOf<list>()
        getUserdata()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var rv=findViewById<RecyclerView>(R.id.rv)
        menuInflater.inflate(R.menu.menu,menu)
        val item=menu?.findItem(R.id.search_action)
        val searchView=item?.actionView as SearchView

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                temparrayList.clear()
                val searchText=newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty())
                {
                    newAL.forEach {
                        if(it.name.toLowerCase(Locale.getDefault()).contains(searchText))
                        {
                            temparrayList.add(it)
                        }
                    }
                    rv.adapter!!.notifyDataSetChanged()
                }
                else
                {
                    temparrayList.clear()
                    temparrayList.addAll(newAL)
                    rv.adapter!!.notifyDataSetChanged()
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getUserdata() {
        for (i in image.indices) {
            val Lists = list(image[i], name[i],dec[i])
            newAL.add(Lists)
        }

        temparrayList.addAll(newAL)
        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)

        var adapter = MyAdapter(temparrayList)
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
                        val archiveItem=temparrayList[viewHolder.adapterPosition]
                        adapter.deleteItem(viewHolder.adapterPosition)
                        adapter.addItem(temparrayList.size,archiveItem)
                        Toast.makeText(this@MainActivity,"Archieved",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val from_pos=viewHolder.adapterPosition
                val to_pos=target.adapterPosition

                Collections.swap(newAL,from_pos,to_pos)
                adapter.notifyItemMoved(from_pos,to_pos)
                return false
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