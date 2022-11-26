import java.util.ArrayList;




public class Jogador{
    public Jogador(){}
    public Jogador(ArrayList<Carta> cartas, int aposta, int posicao){
        Cartas = cartas;
        Aposta = aposta;
        Posicao = posicao;
    }
    public ArrayList<Carta> Cartas;
    public int Aposta;
    public int Posicao;
}
