package org.quarkbit.controlpalabravida.utils;

public class PeriodoTiempo implements Comparable<Object>{

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return horaInicio.toString(false) + " - " + horaFin.toString(false);
	}



	private HoraTiempo horaInicio;
	private HoraTiempo horaFin;
	
	
	public PeriodoTiempo(HoraTiempo horaInicio, HoraTiempo horaFin) {
		super();
		this.horaInicio = new HoraTiempo(horaInicio.getHora(),horaInicio.getMinuto());
		this.horaFin = new HoraTiempo(horaFin.getHora(),horaFin.getMinuto());
	}


	
	public PeriodoTiempo() {
		super();
	}

	public PeriodoTiempo(int horaInicio, int minutoInicio, int segundoInicio,
			int horaFin, int minutoFin, int segundoFin) {
		super();
		this.horaInicio = new HoraTiempo(horaInicio, minutoInicio, segundoInicio);
		this.horaFin = new HoraTiempo(horaFin, minutoFin, segundoFin);
	}

	public PeriodoTiempo(int horaInicio, int minutoInicio, 
			int horaFin, int minutoFin) {
		super();
		this.horaInicio = new HoraTiempo(horaInicio, minutoInicio, -1);
		this.horaFin = new HoraTiempo(horaFin, minutoFin, -1);
	}

	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		PeriodoTiempo periodo = (PeriodoTiempo) obj;

		return this.horaInicio.compareTo(periodo.getHoraInicio());
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horaFin == null) ? 0 : horaFin.hashCode());
		result = prime * result
				+ ((horaInicio == null) ? 0 : horaInicio.hashCode());
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
		PeriodoTiempo other = (PeriodoTiempo) obj;
		if (horaFin == null) {
			if (other.horaFin != null)
				return false;
		} else if (!horaFin.equals(other.horaFin))
			return false;
		if (horaInicio == null) {
			if (other.horaInicio != null)
				return false;
		} else if (!horaInicio.equals(other.horaInicio))
			return false;
		return true;
	}

	public boolean esPeriodoMasLargo(PeriodoTiempo periodo) 
	{
		if(this.equals(periodo))
			return false;
		else
		{
			if(this.compareTo(periodo) == 0)
			{
				if(this.getHoraFin().compareTo(periodo.getHoraFin()) > 0)
					return false;
				else
					return true;
			}
				
			else
			{
				return false;
			}
		}			
	}
	
	public boolean esIgualInicio(PeriodoTiempo periodo)
	{
		return (this.compareTo(periodo) == 0);
	}
	

	/**
	 * @return the horaInicio
	 */
	public HoraTiempo getHoraInicio() {
		return horaInicio;
	}



	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(HoraTiempo horaInicio) {
		this.horaInicio = horaInicio;
	}



	/**
	 * @return the horaFin
	 */
	public HoraTiempo getHoraFin() {
		return horaFin;
	}



	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(HoraTiempo horaFin) {
		this.horaFin = horaFin;
	}
	
	
	public int diferenciaTiempoMinutos(){
		
		return this.horaFin.diferenciaTiempoMinutos(horaInicio);
	}
	
	
	public int diferenciaTiempoLapsoPeriodos(int lapsoPeriodo){
		
		return this.horaFin.diferenciaTiempoMinutos(horaInicio) / lapsoPeriodo;
	}
	
	public boolean estaAntesQue(PeriodoTiempo periodo){
		if(this.horaInicio.compareTo(periodo.getHoraInicio()) > 0)
			return true;
		else
			return false;
	}
	
	/***
	 * Si el Periodo se encuentra entre las 12:00 a 14:00
	 * @return
	 */
	public boolean estaEntreMedioDia()
	{		
		return (this.horaInicio.getHora() == 12 && this.horaFin.getHora() == 14);
	}
	
	/**
	 * compara la hora inicio con la hora que se para como parametro
	 * para verificar que el periodo es nocturno
	 * @param horaInicioNoche solamente la hora por la cual se considera
	 * que es nocturna
	 * @return
	 */
	public boolean esPeriodoNocturno(int horaInicioNoche)
	{
		return this.horaInicio.toMinutos() >= (horaInicioNoche * 60);
	}
	
	
	/**
	 * Compara la Hora inicio de un periodo con las 19:00 
	 * para verificar que ese periodo es nocturno o no
	 * ojo que la hora inicio de periodo nocturno es 19:00
	 * @return
	 */
	public boolean esPeriodoNocturno()
	{
		return this.horaInicio.toMinutos() >= ( 19 * 60);
	}
	
	/**
	 * Retorna la cantidad de lapsos existentes en el periodo seleccionado
	 * para este caso se divide los periodos cada  30 minutos
	 * Por Ejemplo 16:00 a 18:00 -> 1080min - 960 = 120
	 * luego -> 120 min / 30 min = 4 Periodos
	 * cuenta con 4 periodos 
	 * @return
	 */
	public int obtenerCantidadPeriodos()
	{		
		return diferenciaTiempoMinutos()/30;
	}
	
	
	/**
	 * Retorna la cantidad de lapsos existentes en el periodo seleccionado
	 * para este caso se divide los periodos cada  tiempo de Separacion como parametros en minutos
	 * Si tiempoSeparacionPeriodos = 30
	 * Entonces : 
	 * Por Ejemplo 16:00 a 18:00 -> 1080min - 960 = 120
	 * luego -> 120 min / 30 min = 4 Periodos
	 * cuenta con 4 periodos 
	 * @param tiempoSeparacionPeriodos Tiempo de Separacion de los Periodos en minutos, no puede sobrepasar los 60 min
	 * @return
	 */
	public int obtenerCantidadPeriodos(int tiempoSeparacionPeriodos)
	{		
		return diferenciaTiempoMinutos()/tiempoSeparacionPeriodos;
	}
}
