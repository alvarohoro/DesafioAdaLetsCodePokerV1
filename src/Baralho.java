import java.util.ArrayList;

public class Baralho{
    public Baralho(){
        cartas = GerarCartas();
    }

    public ArrayList<Carta> cartas;

    private ArrayList<Carta> GerarCartas(){
        ArrayList<Carta> cartas = new ArrayList<Carta>();
        String[] nomes = { "A","2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] naipes = { "♥","♠","♦","♣"};
        for(int naipe=0; naipe<naipes.length; naipe++){
            for(int nome=0; nome<nomes.length; nome++){
                var carta = new Carta();
                carta.naipe=naipes[naipe];
                carta.nome=nomes[nome];
                cartas.add(carta);
            }
        }
        return cartas;
    }
}