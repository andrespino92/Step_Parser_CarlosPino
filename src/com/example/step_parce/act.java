package com.example.step_parce;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class act extends Fragment
{	
	View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		rootView = inflater.inflate(R.layout.act, container, false);

		String nombre = getArguments().getString("n");
		String color = getArguments().getString("c");
		String imagen = getArguments().getString("i");
		String comen = getArguments().getString("com");
		
		TextView nom = (TextView)rootView.findViewById(R.id.nombre);
		nom.setText(nombre);
		TextView col = (TextView)rootView.findViewById(R.id.color);
		col.setText(color);
		
		ImageView img = (ImageView)rootView.findViewById(R.id.imageView1);
		
		if(color.equals("rojo"))
		{
			img.setImageResource(R.drawable.rojo);
		}
		else
		{
			if(color.equals("azul"))
			{
				img.setImageResource(R.drawable.azul);
			}
			else
			{
				if(color.equals("amarillo"))
				{
					img.setImageResource(R.drawable.amarillo);
				}
				else
				{
					img.setImageResource(R.drawable.marron);
				}
			}
		}
		TextView coment = (TextView)rootView.findViewById(R.id.comen);
		coment.setText(comen);
		
				
		return rootView;
	}	
}