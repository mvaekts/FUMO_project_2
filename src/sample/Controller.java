package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sun.awt.SunHints;

import java.util.Arrays;


public class Controller {
    public TableView ValuesTable;
    public TextField FieldA;
    public TextField FieldB;
    public TableColumn<Point, Double> ColumnK;
    public TableColumn<Point, Double> ColumnY;

    private ObservableList<Point> Points = FXCollections.observableArrayList();

    private double a;
    private double b;

    private double GetRandomValue()
    {
        double value = 100 * Math.random() - 50;
        return Math.round(value * 10000) / 10000.0;
    }

    private void Warning(String msg, String caption)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.setTitle(caption);
        alert.show();
    }


    public void FillRandomClick(MouseEvent mouseEvent)
    {
        a = GetRandomValue();
        b = GetRandomValue();
        ColumnK.setCellValueFactory(new PropertyValueFactory<Point, Double>("X"));
        ColumnY.setCellValueFactory(new PropertyValueFactory<Point, Double>("Y"));
        Points.clear();

        for (int i = 1; i <= 10; ++i)
        {
            Points.add(new Point(GetRandomValue(), 0));
        }

        ValuesTable.setItems(Points);

        FieldA.setText(Double.toString(a));
        FieldB.setText(Double.toString(b));
    }

    public void CalculateClick(MouseEvent mouseEvent)
    {
        if (Points.size() < 1)
        {
            Warning("Заполните таблицу случайными значениями!", "Нечего считать!");
            return;
        }

        try
        {
            a = Double.parseDouble(FieldA.getText());
            b = Double.parseDouble(FieldB.getText());
        }
        catch (Exception e)
        {
            Warning("Ошибка во введенных коэфициентах a и b!", "Неверный ввод!");
            return;
        }

        if (a == 0 && b == 0)
        {
            Warning("Оба значения a и b не должны быть равны 0!", "Невозможно вычислить!");
        }

        for (int i = 0; i < 10; ++i)
        {
            double x = Points.get(i).getX();

            double sum = 0;
            for (int j = 0; j < i; j++)
            {
                sum += Points.get(j).getX();
            }
            double cos = Math.cos(x);
            double result = Math.sqrt((cos * cos)/((a * a + b * b) - Math.sin(x))) * sum;
            result = Math.round(result * 10000) / 10000.0;

            Points.get(i).setY(result);
        }

        ValuesTable.refresh();

    }

    public void ClearClick(MouseEvent mouseEvent)
    {
        a = 0;
        b = 0;
        FieldA.setText("");
        FieldB.setText("");
        Points.clear();
        ValuesTable.refresh();
    }

    public void ExitClick(MouseEvent mouseEvent)
    {
        Platform.exit();
    }
}
