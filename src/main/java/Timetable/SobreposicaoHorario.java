package Timetable;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Classe responsável por identificar a sobreposição de horários entre eventos
 * do calendário.
 */
public class SobreposicaoHorario {

    /**
     * 
     * Identifica as datas e horas com sobreposição de eventos a partir de uma lista
     * de eventos do calendário.
     * 
     * @param Cevents Lista de eventos do calendário.
     * 
     * @return Mapa contendo as datas e horas com sobreposição de eventos e uma
     *         lista com os CalendarEvents que ocorrem nessa data
     */
    public static Map<Date, List<CalendarEvent>> SobreposHorario(List<CalendarEvent> Cevents) {
        Map<Date, List<CalendarEvent>> mapa = new HashMap<>();
        Map<Date, List<CalendarEvent>> mapaComSobreposicoes = new HashMap<>();

        for (CalendarEvent ce : Cevents) {

            
            if (ce.getStartDate() != null) {
                //A MINHA IDEIA ERA DENTRO DESTE IF, VERIFICAR TB SE A DATA INICIAL DO CE (QUE PEGAMOS NO FOR)
                // ESTÁ ENTRE A DATA INICIAL E A DATA FINAL DE ALGUM QUE ESTEJA JA POSTO NO MAPA, TENS O PRINT QUE TE MANDEI COM UMA VAGA IDEIA
                // CASO ESTIVESSE, ADICIONAR A UM CAMPO NOVO NO MAPA, ADICIONAMOS À DATA INICIAL DO QUE ESTÁ NO MAPA
                //EXEMPLO:
                //TEMOS UM EVENTO "A" COM STARTDATE AS 15:00:00, E NO MAPA HÁ UM EVENTO "B" QUE COMECA AS 14:00:00 E VAI ATÉ AS 16:00:00, 
                //EU QUERO ADICIONAR À LISTA DE EVENTOS ASSIOCIADA AO EVENTO B, ATRAVES DO B.GETSTARTDATE(), O EVENTO "A". 
                //SE NÃO EXISTIR NO MAPA, NEM ESTÁ ENTRE OUTRO EVENTO, ADICIONAR NO MAPA
    
                if (!mapa.containsKey(ce.getStartDate())) {
                    List<CalendarEvent> a = new ArrayList<>();
                    a.add(ce);
                    mapa.put(ce.getStartDate(), a);
                }

                List<CalendarEvent> eventosNoDia = mapa.get(ce.getStartDate());
                eventosNoDia.add(ce);
            }
        }

        for (Date b : mapa.keySet()) {
            if (mapa.get(b).size() > 1) {
                // paarte do boolean
                for (CalendarEvent a : mapa.get(b)) {
                    a.setIsSobreposto(true);
                }
                mapaComSobreposicoes.put(b, mapa.get(b));
            }
        }
        return mapaComSobreposicoes;
    }

    public static void main(String[] args) throws ParseException, IOException {
        List<CalendarEvent> eventos = showCSV.showHorario(new File("output.csv"));
        CalendarEvent b = new CalendarEvent("OLA", "xd", new Date(2022, 5, 12, 15, 30, 0),
                new Date(2022, 5, 12, 17, 0, 0));
        eventos.add(b);
        eventos.add(b);
        Map<Date, List<CalendarEvent>> mapa = SobreposicaoHorario.SobreposHorario(eventos);

        for (Date d : mapa.keySet()) {
            int count = 0;

            for (CalendarEvent e : mapa.get(d)) {
                count = count + 1;
                System.out.println(e.getTitle() + e.getIsSobreposto());
            }
            System.out.println(d + " " + count);

        }
    }
}
