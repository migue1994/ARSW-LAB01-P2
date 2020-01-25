package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;

    private static Canodromo can;

    private static RegistroLlegada reg = new RegistroLlegada();   

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        //Acción del botón start
        can.setStartAction(
                new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
						//como acción, se crea un nuevo hilo que cree los hilos
                        //'galgos', los pone a correr, y luego muestra los resultados.
                        //La acción del botón se realiza en un hilo aparte para evitar
                        //bloquear la interfaz gráfica.
                        ((JButton) e.getSource()).setEnabled(false);
                        for (int i = 0; i < can.getNumCarriles(); i++) {
                            galgos[i] = new Galgo(can.getCarril(i), "" + i, reg , can);
                        }
                        
                         new Thread() {
                            public void run() {
                               synchronized (this) {
                               	for (int i = 0; i < can.getNumCarriles(); i++) {
                                    galgos[i].start();
                                }
                               }
                            }
                         }.start();
                    }
                }   
        );          
                               
//							   try {
//								join();
//	                               synchronized (this) {
//	                              		can.winnerDialog(reg.getGanador(),reg.getUltimaPosicionAlcanzada() - 1); 
//	                                   System.out.println("El ganador fue:" + reg.getGanador());
//	                                  }
//							   } catch (InterruptedException e) {
//								   e.printStackTrace();
//							   }

                               /*try {
									join();
									new Thread() {
	                                	@Override
	                                	public void run() {
	                                		can.winnerDialog(reg.getGanador(),reg.getUltimaPosicionAlcanzada() - 1); 
	                                        System.out.println("El ganador fue:" + reg.getGanador());
	                                    
	                                        try {
												join();
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
	                                	}
	                                	
	                                }.start();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
                                System.out.println("Aqui");*/
//                            }
//                        }.start();
//                    }
//                }
//        );

        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < can.getNumCarriles(); i++){
						galgos[i].pausar();
						}
                        System.out.println("Carrera pausada!");
                    }
                }
        );

        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < can.getNumCarriles(); i++){
						galgos[i].reaunudar();
						}
                        System.out.println("Carrera reanudada!");
                    }
                }
        );

    }

}
