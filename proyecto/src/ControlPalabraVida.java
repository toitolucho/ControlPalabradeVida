import org.quarkbit.controlpalabravida.formularios.FAutenticacion;
import org.quarkbit.controlpalabravida.utils.FormUtilities;


public class ControlPalabraVida {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FAutenticacion formAutenticacion = new FAutenticacion();
		FormUtilities.centrar(formAutenticacion);
		formAutenticacion.setVisible(true);
//		formAutenticacion.dispose();
	}

}
