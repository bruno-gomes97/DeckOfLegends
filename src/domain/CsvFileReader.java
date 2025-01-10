package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {

    public static void main(String[] args) {
        CsvFileReader obj = new CsvFileReader();
        List<Card> cards = obj.readCards();


        for (Object card : cards) {
            System.out.println(card);
        }
    }

    public List<Card> readCards() {
        String arquivoCSV = "src/data/deck.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        List<Card> cards = new ArrayList<>();

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoCSV), "UTF-8"));
            while ((linha = br.readLine()) != null) {


                String[] valores = linha.split(csvDivisor);


                if (valores.length == 6) {
                    String tipo = valores[4].trim();

                    if (tipo.equalsIgnoreCase("Efeito")) {

                        Effect effect = new Effect(
                                valores[0],
                                valores[1],
                                Integer.parseInt(valores[2].trim()),
                                Integer.parseInt(valores[3].trim()),
                                Target.valueOf(valores[5].toUpperCase().trim())
                        );
                        cards.add(effect);
                    } else if (tipo.equalsIgnoreCase("Monstro")) {

                        Monster monster = new Monster(
                                valores[0],
                                valores[1],
                                Integer.parseInt(valores[2].trim()),
                                Integer.parseInt(valores[3].trim())
                        );
                        cards.add(monster);
                    } else {
                        System.err.println("Tipo desconhecido: " + tipo);
                    }
                } else {
                    System.err.println("Linha inválida: " + linha);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter números: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Valor inválido para Target: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cards;
    }
}
