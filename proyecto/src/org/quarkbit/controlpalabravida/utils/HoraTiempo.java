package org.quarkbit.controlpalabravida.utils;
import java.util.Calendar;
import java.util.Date;

public class HoraTiempo implements Comparable<Object>{ 
	private int hora;
	private int minuto;
	private int segundo;
	
	
	public HoraTiempo(int hora, int minuto, int segundo) {
		super();
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}
	
	public HoraTiempo(HoraTiempo horaTiempo) {
		super();
		this.hora = horaTiempo.hora;
		this.minuto = horaTiempo.minuto;
		this.segundo = horaTiempo.segundo;
	}


	public HoraTiempo(int hora, int minuto) {
		super();
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = 0;
	}


	public HoraTiempo() {
		super();
		this.hora = 0;
		this.minuto = 0;
		this.segundo = -1;
	}


	public int getHora() {
		return hora;
	}


	public void setHora(int hora) {
		this.hora = hora;
	}


	public int getMinuto() {
		return minuto;
	}


	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}


	public int getSegundo() {
		return segundo;
	}


	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hora;
		result = prime * result + minuto;
		result = prime * result + segundo;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoraTiempo other = (HoraTiempo) obj;
		if (hora != other.hora)
			return false;
		if (minuto != other.minuto)
			return false;
		if (segundo != other.segundo)
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(segundo > 0)
			return "HoraTiempo [hora=" + hora + ", minuto=" + minuto + ", segundo="
				+ segundo + "]";
		else
			return ((hora < 10) ? "0" + String.valueOf(hora) : String.valueOf(hora)) + " : " +
					((minuto < 10 ) ? "0" + String.valueOf(minuto) : String.valueOf(minuto));
	}
	
	public String toString(boolean withSeconds){		
		if(withSeconds)
			return (hora < 10) ? "0" + String.valueOf(hora) : String.valueOf(hora) + " : " +
					((minuto < 10 ) ? "0" + String.valueOf(minuto) : String.valueOf(minuto))+
					((segundo < 10 ) ? "0" + String.valueOf(segundo) : String.valueOf(segundo));
		else
			return ((hora < 10) ? "0" + String.valueOf(hora) : String.valueOf(hora)) + ":" +
					((minuto < 10 ) ? "0" + String.valueOf(minuto) : String.valueOf(minuto));
	}

	
	public void addMinuts(int minuts)
	{
		if(this.minuto + minuts > 59)
		{
			this.hora += (this.minuto + minuts) / 60;
			this.minuto = (this.minuto + minuts) % 60;			
			//this.hora++;
			if(this.hora == 24)
				this.hora = 0;
		}
		else
			this.minuto += minuts;	
		if(this.minuto < 0 && this.hora == 0)
			this.hora = 23;
		if(this.minuto < 0)
			this.minuto = Math.abs(this.minuto);
	}
	
	
	@Override
	public int compareTo(Object obj) {
		HoraTiempo horaTiempo = (HoraTiempo) obj;
		Calendar horaFija = Calendar.getInstance();
		Calendar horaParametro = (Calendar) horaFija.clone();
		
		horaFija.set(Calendar.HOUR, this.hora);
		horaFija.set(Calendar.MINUTE, this.minuto);
		horaFija.set(Calendar.SECOND, this.segundo);
		
		
		horaParametro.set(Calendar.HOUR, horaTiempo.getHora());
		horaParametro.set(Calendar.MINUTE, horaTiempo.getMinuto());
		horaParametro.set(Calendar.SECOND, horaTiempo.getSegundo());

		return horaFija.compareTo(horaParametro);		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		HoraTiempo horaTiempo = new HoraTiempo();
		horaTiempo.setHora(this.hora);
		horaTiempo.setMinuto(this.minuto);
		horaTiempo.setSegundo(this.segundo);
		return horaTiempo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	/**
	 * Realiza la diferencia de tiempos entre dos HoraTiempo
	 * por ejemplo 18:15  -  14:30 = 225min
	 * @param horaTiempo hora con la Cual se va a restar ( 14:30 para el ejemplo)
	 * @return la diferencia de tiempos en minustos ( 225min para el ejemplo)
	 */
	
	public int diferenciaTiempoMinutos(HoraTiempo horaTiempo){
		int minutos1 = 0;
		int minutos2 = 0;
		
		minutos1 = this.hora * 60 + minuto;
		minutos2 = horaTiempo.getHora() * 60 + horaTiempo.getMinuto();
		
		return minutos1 - minutos2;
	}
	
	
	/**
	 * Realiza la diferencia de tiempos entre dos HoraTiempo
	 * por ejemplo 18:15  -  14:30 = 3:45	 * 
	 * @param horaTiempo hora con la Cual se va a restar ( 14:30 para el ejemplo)
	 * @return la diferencia de tiempos representada por la instancia de la clase( 3:45 para el ejemplo)
	 */
	public HoraTiempo diferenciaTiempoHoras
	(HoraTiempo horaTiempo){
		
		HoraTiempo horaTiempoRetorno;
		int minutos1 = 0;
		int minutos2 = 0;
		
		minutos1 = this.hora * 60 + minuto;
		minutos2 = horaTiempo.getHora() * 60 + horaTiempo.getMinuto();
		
		horaTiempoRetorno = new HoraTiempo((minutos1-minutos2)/60 , (minutos1-minutos2)%60);
		return horaTiempoRetorno;
	}
	
	public Date toDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, hora);
		cal.set(Calendar.MINUTE, minuto);
		cal.set(Calendar.SECOND, segundo);
		
		return cal.getTime();
		
	}
	
	public static HoraTiempo toHoraTiempo(Date horaTiempo )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaTiempo);
		
		
		return new HoraTiempo(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
		
	}
	
	public int toMinutos(){
		return this.hora*60 + this.minuto;
	}	
	
	
	public boolean esHoraTope()
	{
		return (this.hora == 0) && (this.minuto == 0) && (this.segundo == 0);
	}
	
	public boolean esHoraCoherente()
	{
		return this.hora<=23 && this.minuto <=59 && this.segundo <=59;
	}
}
