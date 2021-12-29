/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.dogglers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogglers.R
import com.example.dogglers.const.Layout
import com.example.dogglers.data.DataSource
import com.example.dogglers.model.Dog

/**
 * Adapter to inflate the appropriate list item layout and populate the view with information
 * from the appropriate data source
 */
class DogCardAdapter(
    private val context: Context?,
    private val layout: Int
): RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    private val data: List<Dog> = DataSource.dogs

    class DogCardViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val dogImageView: ImageView = view.findViewById(R.id.dogImageView)
        val dogName: TextView = view.findViewById(R.id.dogNameTextView)
        val dogAge: TextView = view.findViewById(R.id.dogAgeTextView)
        val dogHobbies: TextView = view.findViewById(R.id.dogHobbiesTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        val view: View = when(layout){
            Layout.GRID -> LayoutInflater.from(parent.context).inflate(R.layout.grid_list_item, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.vertical_horizontal_list_item, parent, false)
        }
        return DogCardViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {
        val item = data[position]
        holder.dogImageView.setImageResource(item.imageResourceId)
        holder.dogName.text = item.name
        holder.dogAge.text = context?.resources?.getString(R.string.dog_age, item.age)
        holder.dogHobbies.text = context?.resources?.getString(R.string.dog_hobbies, item.hobbies)
    }
}
