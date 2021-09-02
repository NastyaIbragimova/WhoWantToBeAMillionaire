package com.example.whowanttobeamillionaire;

import  android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivityMillionaire extends AppCompatActivity {

    private TextView tvTaskMillionaire, tvExitMillionaire, tvFiftyMillionaire, tvTimerMillionaire, tvSumMillionaire, tvSumTaskMillionaire;
    private final ArrayList<TextView> variantsMillionaire = new ArrayList<>();
    private final ArrayList<TaskMillionaire> tasksMillionaire = new ArrayList<>();
    private int questionCountMillionaire, timeCountMillionaire, playerSumMillionaire;
    private CountDownTimer cdtMillionaire;
    private TaskMillionaire currentTaskMillionaire;
    private Handler handlerMillionaire;
    private Runnable runnableWinMillionaire, runnableLoseMillionaire;
    private final int[] sumArrMillionaire = new int[]{0, 500, 1000, 2000, 3000, 5000, 10000, 15000, 25000, 50000, 100000, 200000, 400000, 800000, 1500000, 3000000};
    private Dialog dialogEndMillionaire, dialogStartMillionaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogStartMillionaire = new Dialog(this);
        dialogStartMillionaire.setContentView(R.layout.dialog_start_millionaire);
        dialogStartMillionaire.show();
    }

    private void initMillionaire() {
        tvTaskMillionaire = findViewById(R.id.tv_task_millionaire);
        tvExitMillionaire = findViewById(R.id.tv_exit_millionaire);
        tvFiftyMillionaire = findViewById(R.id.tv_fifty_millionaire);
        tvTimerMillionaire = findViewById(R.id.tv_timer_millionaire);
        tvSumMillionaire = findViewById(R.id.tv_sum_millionaire);
        tvSumTaskMillionaire = findViewById(R.id.tv_task_sum_millionaire);
        questionCountMillionaire = 1;
        timeCountMillionaire = 60;
        setVariantsButtonMillionaire();
        setTimersMillionaire();
        addTasksMillionaire();
    }

    private void setTimersMillionaire() {
        handlerMillionaire = new Handler();
        runnableWinMillionaire = () -> {
            currentTaskMillionaire = null;
            playerSumMillionaire = sumArrMillionaire[questionCountMillionaire];
            questionCountMillionaire++;
            setQuestionMillionaire();
            setButtonEnabledMillionaire(true);
        };
        runnableLoseMillionaire = () -> {
            currentTaskMillionaire = null;
            setDialogEndMillionaire("Вы проиграли!");
        };

        cdtMillionaire = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeCountMillionaire--;
                tvTimerMillionaire.setText("00:" + timeCountMillionaire);
            }

            @Override
            public void onFinish() {
                tvTimerMillionaire.setText("Время вышло!");
            }
        };
    }

    private void setVariantsButtonMillionaire() {
        Display displayMillionaire = getWindowManager().getDefaultDisplay();
        Point sizeMillionaire = new Point();
        displayMillionaire.getSize(sizeMillionaire);
        int displayWidthMillionaire = sizeMillionaire.x;
        variantsMillionaire.add(findViewById(R.id.tv_variant1_millionaire));
        variantsMillionaire.add(findViewById(R.id.tv_variant2_millionaire));
        variantsMillionaire.add(findViewById(R.id.tv_variant3_millionaire));
        variantsMillionaire.add(findViewById(R.id.tv_variant4_millionaire));
        for (int i = 0; i < variantsMillionaire.size(); i++) {
            variantsMillionaire.get(i).setWidth((displayWidthMillionaire / 2) - 100);
            variantsMillionaire.get(i).setHeight(150);
        }
    }

    private void setDialogEndMillionaire(String s) {
        dialogEndMillionaire = new Dialog(this);
        dialogEndMillionaire.setContentView(R.layout.dialog_end_millionaire);
        TextView tvResMillionaire = dialogEndMillionaire.findViewById(R.id.tv_res_millionaire);
        tvResMillionaire.setText(s);
        dialogEndMillionaire.show();
    }

    private boolean checkAnswerMillionaire(String s) {
        return s.equals(currentTaskMillionaire.getQuestion()[1]);
    }

    private void setQuestionMillionaire() {
        if (questionCountMillionaire > 15) {
            setDialogEndMillionaire("Вы победили!\nВаш выигрыш " + playerSumMillionaire + " р");
        } else {
            Collections.shuffle(tasksMillionaire);
            tvSumTaskMillionaire.setText("Вопрос номер " + questionCountMillionaire + "\nСтоимость вопроса: " + sumArrMillionaire[questionCountMillionaire] + " р");
            tvSumMillionaire.setText("Выигрыш " + playerSumMillionaire + " р");
            timeCountMillionaire = 60;
            for (int i = 0; i < tasksMillionaire.size(); i++) {
                if (tasksMillionaire.get(i).getLevel() == questionCountMillionaire) {
                    currentTaskMillionaire = tasksMillionaire.get(i);
                    break;
                }
            }
            tvTaskMillionaire.setText(currentTaskMillionaire.getQuestion()[0]);
            Collections.shuffle(variantsMillionaire);
            for (int i = 0; i < variantsMillionaire.size(); i++) {
                variantsMillionaire.get(i).setText(currentTaskMillionaire.getQuestion()[i + 1]);
            }
            cdtMillionaire.start();
        }
    }

    public void onClickVariantMillionaire(View view) {
        TextView tv = (TextView) view;
        setButtonEnabledMillionaire(false);
        cdtMillionaire.cancel();
        if (checkAnswerMillionaire(tv.getText().toString())) {
            tv.setBackgroundResource(R.drawable.button_true_millionaire);
            handlerMillionaire.postDelayed(runnableWinMillionaire, 1000);
        } else {
            tv.setBackgroundResource(R.drawable.button_false_millionaire);
            handlerMillionaire.postDelayed(runnableLoseMillionaire, 1000);
        }
    }

    private void setButtonEnabledMillionaire(boolean b) {
        for (int i = 0; i < variantsMillionaire.size(); i++) {
            variantsMillionaire.get(i).setEnabled(b);
            variantsMillionaire.get(i).setBackgroundResource(R.drawable.button_millionaire);
        }
    }

    public void onClickTakeMoneyMillionaire(View view) {
        setDialogEndMillionaire("Вы забрали деньги!\nВаш выигрыш " + playerSumMillionaire + " р");
    }

    public void onClickFiftyMillionaire(View view) {
        tvFiftyMillionaire.setEnabled(false);
        tvFiftyMillionaire.setBackgroundResource(R.drawable.button_fifty_millionaire);
        for (int i = 0; i < variantsMillionaire.size(); i++) {
            if (variantsMillionaire.get(i).getText().toString().equals(currentTaskMillionaire.getQuestion()[3]) ||
                    variantsMillionaire.get(i).getText().toString().equals(currentTaskMillionaire.getQuestion()[4])) {
                variantsMillionaire.get(i).setEnabled(false);
                variantsMillionaire.get(i).setBackgroundResource(R.drawable.button_fifty_millionaire);
            }
        }
    }

    public void onClickExitGameMillionaire(View view) {
        dialogEndMillionaire.dismiss();
        finish();
    }

    public void onClickRestartMillionaire(View view) {
        dialogEndMillionaire.dismiss();
        restartGameMillionaire();
    }

    private void restartGameMillionaire() {
        tvFiftyMillionaire.setBackgroundResource(R.drawable.button_millionaire);
        playerSumMillionaire = 0;
        questionCountMillionaire = 1;
        setButtonEnabledMillionaire(true);
        tvFiftyMillionaire.setEnabled(true);
        setQuestionMillionaire();
    }

    public void onClickStartGameMillionaire(View view) {
        dialogStartMillionaire.dismiss();
        setContentView(R.layout.activity_main_millionaire);
        initMillionaire();
        setQuestionMillionaire();
    }

    private void addTasksMillionaire() {
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что за место, попав в которое, человек делает селфи на кухне, которую не может себе позволить?", "Икея", "Рим", "Лондон", "Париж"}, 1));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какой город объявлен официальной родиной русского Деда Мороза?", "Великий Устюг", "Малая Вишера", "Вышний Волочек", "Нижний Новгород"}, 1));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"К кому первому обратились за помощью дед и бабка, не справившись с репкой?", "К внучке", "К Жучке", "К дочке", "К залу"}, 2));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Кого нет среди смешариков?", "Коня", "Свиньи", "Барана", "Лося"}, 2));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какое растение существует на самом деле?", "Лох индийский", "Лох чилийский", "Лох греческий", "Лох русский"}, 3));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что проводит боксер, наносящий удар противнику снизу?", "Апперкот", "Свинг", "Хук", "Джэб"}, 3));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Как называется ближайшая к Земле звезда?", "Солнце", "Полярная", "Сириус", "Проксиома Центавра"}, 4));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что помогает запомнить мнемоническое правило «Это я знаю и помню прекрасно»?", "Число Пи", "Порядок падежей", "Цвета радуги", "Ряд активности металлов"}, 4));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какую площадь имеет клетка стандартной школьной тетради?", "0.25 кв.см", "1 кв.см", "0.5 кв.см", "1.25 кв.см"}, 5));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что происходит на соревнованиях по стрельбе, если соперники набрали одинаковое количество очков?", "Перестрелка", "Перевербовка", "Перепалка", "Перебранка"}, 5));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что вращается вокруг Земли?", "Луна", "Солнце", "Марс", "Венера"}, 6));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Как назывались старинные русские пушки-гаубицы?", "Единорог", "Василиск", "Грифон", "Кентавр"}, 6));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Сколько раз в сутки подзаводят куранты Спасской башни Кремля?", "Два", "Один", "Три", "Четыре"}, 7));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Кто 1-м получил Нобелевскую премию по литературе?", "Поэт", "Романист", "Драматург", "Эссеист"}, 7));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"С какой буквы начинаются слова, опубликованные в 16-м томе последнего издания Большой советской энциклопедии?", "М", "О", "П", "Н"}, 8));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Кто из перечисленных был пажом во времена Екатерины II?", "А.Н. Радищев", "Н.М. Карамзин", "Г.Р. Державин", "Д.И. Фонвизин"}, 8));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какой химический элемент назван в честь злого подземного гнома?", "Кобальт", "Гафний", "Бериллий", "Теллур"}, 9));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"В какой из этих столиц бывших союзных республик раньше появилось метро?", "Тбилиси", "Ереван", "Минск", "Баку"}, 9));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Сколько морей омывают Балканский полуостров?", "6", "3", "4", "5"}, 10));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Реки с каким названием нет на территории России?", "Спина", "Уста", "Палец", "Шея"}, 10));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что такое лобогрейка?", "Жнейка", "Шапка", "Печка", "Болезнь"}, 11));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какой роман Фенимор Купер написал на спор с женой?", "«Предосторожность»", "«Зверобой»", "«Пионеры»", "«Последний из могикан»"}, 11));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какой вид кавалерии предназначался для боевых действий как в конном, так и в пешем строю?", "Драгуны", "Гусары", "Уланы", "Кирасиры"}, 12));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какое имя не принимал ни один папа римский?", "Георгий", "Виктор", "Евгений", "Валентин"}, 12));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"В каком немецком городе родилась будущая императрица России Екатерина II?", "Штеттин", "Цербст", "Дармштадт", "Висбаден"}, 13));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Что запрещал указ, который в 1726 году подписала Екатерина I?", "Пускать пыль в глаза", "Бить баклуши", "Переливать из пустого в порожнее", "Точить лясы"}, 13));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Кто из этих деятелей искусства стал директором первого профессионального публичного театра России?", "Александр Сумароков", "Василий Каратыгин", "Павел Молчанов", "Яков Княжнин"}, 14));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Из какого фильма в наш обиход вошло слово «папарацци»?", "«Сладкая жизнь»", "«Восемь с половиной»", "«Ночи Кабирии»", "«Дорога»"}, 14));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"Какое государство ежегодно дарит Лондону ёлку для Трафальгарской площади?", "Норвегия", "Дания", "Швеция", "Эстония"}, 15));
        tasksMillionaire.add(new TaskMillionaire(new String[]{"На самолёте какого авиаконструктора экипаж Валерия Чкалова совершил первый беспосадочный перелёт из СССР в США?", "Туполева", "Яковлева", "Ильюшина", "Антонова"}, 15));
        Collections.shuffle(tasksMillionaire);
    }
}