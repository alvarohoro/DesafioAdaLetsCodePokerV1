import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;



public class DesafioPoker {
    Baralho baralho = new Baralho();
    ArrayList<Jogador> jogadores = new ArrayList<Jogador>();

    public static void main(String[] args){
        DesafioPoker desafioPoker = new DesafioPoker();
        desafioPoker.embaralha();
        Scanner sc = new Scanner(System.in);

        System.out.println("Quantos jogadores estão na mesa?");
        try{
            var n_jogadores = sc.nextInt();
            if(n_jogadores<2){
                throw new Exception("Devem existir ao menos 2 jogadores.");
            }
            ArrayList<Jogador> jogadores =  desafioPoker.jogadores;
            int valorApostaMaxima = 0;
            int valorAbandonados=0;

            for(int i=0; i<n_jogadores;i++){
                ArrayList<Carta> cartas = desafioPoker.darCartas(2);
                System.out.println("Jogador "+(i+1));
                desafioPoker.imprimeCartas(cartas);
                System.out.println("O valor da aposta atual é "+valorApostaMaxima);
                while(true){
                    System.out.println("Opções: [C]heck, [F]old, [B]et, [R]aise");
                    String opcao = sc.next();
                    if (opcao.equals("C")) {
                        if(valorApostaMaxima==0){
                            Jogador jogador = new Jogador(cartas,0,i);
                            jogadores.add(jogador);
                            System.out.println("O jogador deu check.");
                            break;
                        }else{
                            System.out.println("Opção inválida, já há uma aposta diferente de zero.");

                        }
                    } else if (opcao.equals("F")) {
                        System.out.println("Jogador saiu do jogo.");
                        break;
                    }else if (opcao.equals("B")) {
                        Jogador jogador = new Jogador(cartas, valorApostaMaxima,i);
                        jogadores.add(jogador);

                        System.out.println("Jogador igualou o valor máximo.");
                        break;
                    }else if (opcao.equals("R")) {
                        while (true){
                            System.out.println("Digite o valor da sua aposta");
                            int aposta = sc.nextInt();
                            System.out.println("O jogador apostou "+aposta);
                            if (aposta < valorApostaMaxima) {
                                System.out.println("Valor inválido. Digite um valor superior à " + valorApostaMaxima);
                            } else {
                                valorApostaMaxima = aposta;
                                Jogador jogador = new Jogador(cartas, valorApostaMaxima,i);
                                jogadores.add(jogador);

                                break;
                            }
                        }
                        break;
                    }
                }

                System.out.println("---------------------------");
                desafioPoker.imprimirStatus(jogadores, valorAbandonados);
            }


            while(!desafioPoker.verificaIgualdadeApostas()){
                Iterator<Jogador> i = jogadores.iterator();
                while(i.hasNext()){
                    //for(Jogador j: jogadores){
                    Jogador j = i.next();
                    System.out.println("Jogador "+(j.Posicao));
                    desafioPoker.imprimeCartas(j.Cartas);
                    System.out.println("O valor da aposta atual é "+valorApostaMaxima);
                    while(true){
                        System.out.println("Opções: [C]heck, [F]old, [B]et, [R]aise");
                        String opcao = sc.next();
                        if (opcao.equals("C")) {
                            if(valorApostaMaxima==0){
                                System.out.println("O jogador deu check.");
                                break;
                            }else{
                                System.out.println("Opção inválida, já há uma aposta diferente de zero.");

                            }
                        } else if (opcao.equals("F")) {
                            valorAbandonados+=j.Aposta;
                            i.remove();
                            System.out.println("Jogador saiu do jogo.");
                            break;
                        }else if (opcao.equals("B")) {
                            j.Aposta=valorApostaMaxima;
                            System.out.println("Jogador igualou o valor máximo.");
                            break;
                        }else if (opcao.equals("R")) {
                            while (true){
                                System.out.println("Digite o valor da sua aposta");
                                int aposta = sc.nextInt();
                                System.out.println("O jogador apostou "+aposta);
                                if (aposta < valorApostaMaxima) {
                                    System.out.println("Valor inválido. Digite um valor superior à " + valorApostaMaxima);
                                } else {
                                    valorApostaMaxima = aposta;
                                    j.Aposta=aposta;
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    System.out.println("---------------------------");
                    desafioPoker.imprimirStatus(jogadores, valorAbandonados);

                    if(desafioPoker.verificaIgualdadeApostas()){
                        ArrayList<Carta> cartasMesa = desafioPoker.darCartas(5);
                        System.out.println("Cartas da mesa:");
                        desafioPoker.imprimeCartas(cartasMesa);
                        while(true){
                            System.out.println("Quem ganhou?");
                            int ganhador = sc.nextInt();
                            Boolean jogadorValido = jogadores.stream().anyMatch(x -> x.Posicao==ganhador);
                            if(jogadorValido){
                                System.out.println("O Jogador "+ganhador+" levou "+(jogadores.size()*valorApostaMaxima+valorAbandonados));
                                break;
                            }else{
                                System.out.println("Jogador inválido");
                            }
                        }

                        break;

                    }
                }

            }
        }
        catch (Exception e){
            System.out.println(e);
        }


    }



    public void imprimirStatus(ArrayList<Jogador> jogadores, int valorAbandonados){
        System.out.println("Jogadores, suas cartas e suas apostas:");

        for (Jogador j:jogadores) {
            System.out.print("Jogador "+j.Posicao +", apostou "+j.Aposta+" com a mão ");
            for(Carta c:j.Cartas){
                System.out.print(c.nome+c.naipe+" ");
            }
            System.out.println("");

        }
        System.out.println("Valor de jogadores que abandonaram = "  + valorAbandonados);
        System.out.println("---------------------------");


    }
    public void embaralha(){
        var cartas = baralho.cartas;
        for(int i=0;i<400;i++){
            var random = new Random();
            var numero1 = random.ints(0,cartas.size()-1).findFirst().getAsInt();
            var numero2 = random.ints(0,cartas.size()-1).findFirst().getAsInt();
            var carta1 = cartas.get(numero1);
            var carta2 = cartas.get(numero2);
            cartas.set(numero1, carta2);
            cartas.set(numero2, carta1);
        }
        baralho.cartas=cartas;
    }

    /*public ArrayList<Carta> darCartas(){
        var cartas = new ArrayList<Carta>();
        if(baralho.cartas.size()>=2){
            var carta1 = baralho.cartas.get(0);
            var carta2 = baralho.cartas.get(1);
            baralho.cartas.remove(1);
            baralho.cartas.remove(0);
            cartas.add(carta1);
            cartas.add(carta2);
        }
        return cartas;
    }*/

    public ArrayList<Carta> darCartas(int n){
        var cartas = new ArrayList<Carta>();
        if(baralho.cartas.size()>=n){
            for(int i=0;i<n;i++){
                var carta = baralho.cartas.get(0);
                baralho.cartas.remove(0);
                cartas.add(carta);
            }

        }
        return cartas;
    }

    public Boolean temCarta(){
        return (baralho.cartas.size()>0) ? true : false;
    }

    public void imprimeBaralho(){
        for(int i=0; i<baralho.cartas.size();i++){
            var carta = baralho.cartas.get(i);
            System.out.println(carta.nome + carta.naipe + "  ");
        }
    }

    public void imprimeCartas(ArrayList<Carta> cartas){
        for(int i=0; i<cartas.size();i++){
            var carta = cartas.get(i);
            System.out.print(carta.nome + carta.naipe + "  ");
        }
        System.out.println("");
    }

    public Boolean verificaIgualdadeApostas(){
        int valorAposta = jogadores.stream().findFirst().get().Aposta;
        for (Jogador j:jogadores) {
            if(j.Aposta!=valorAposta){
                return false;
            }
        }
        return true;
    }

}
