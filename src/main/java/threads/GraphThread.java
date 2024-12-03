package threads;

import org.example.HistoricalData;
import org.ta4j.core.BarSeries;
import window.Graph;

/**
 * ez az osztály felelős az a grafikon urjarajzolásáért
 */
public class GraphThread  extends Thread {
    private Graph graph;
    private HistoricalData data;
    private String symbol;
    private String timeframe;
    private String start;
    private String end;

    public GraphThread(Graph graph) {
        symbol = "AAPL";
        timeframe = "5Min";
        start = "2024-01-01";
        end = "2024-01-02";
        this.graph = graph;
        data = new HistoricalData();
    }

    /**
     * ha a grafikonon változás történik eltárolja a szükséges adatokat hogy késöbb ismét ujra tudja rajzolni
     * @param symbol
     * @param timeframe
     * @param start
     * @param end
     */
    public void setBars(String symbol, String timeframe, String start, String end){
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.start = start;
        this.end = end;
        BarSeries stock = data.GetChart(symbol, timeframe, start, end);
        graph.setStock(stock);
    }
    public void run() {
        while(true){
            BarSeries stock = data.GetChart(symbol, timeframe, start, end);
            graph.setStock(stock);
            long sec = 1000;
            try {
                switch (timeframe) {
                    case "1Min":
                        sleep(15 * sec);
                        break;
                    case "5Min":
                        sleep(60 * sec);
                        break;
                    case "15Min":
                        sleep(300 * sec);
                        break;
                    case "30Min":
                        sleep(600 * sec);
                        break;
                    case "1Hour":
                        sleep(1200 * sec);
                        break;
                    default:
                        sleep(36000 * sec);
                }
            }catch(InterruptedException e){

            }

        }
    }
}
