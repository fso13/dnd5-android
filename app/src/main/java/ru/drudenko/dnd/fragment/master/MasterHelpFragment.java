package ru.drudenko.dnd.fragment.master;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.drudenko.dnd.R;
import ru.drudenko.dnd.activity.HelpActivity;
import ru.drudenko.dnd.adapter.SingleAdapter;
import ru.drudenko.dnd.model.CustomItem;


public class MasterHelpFragment extends ListFragment {
    public static final String CASE_7 = "Модификатор характеристики: (Значение характеристики - 10) / 2. Округление в меньшую сторону.<br>" +
            "КД персонажа без доспеха: 10 + модификатор Ловкости<br>" +
            "Безоружный удар: 1к20 + бонус мастерства + модификатор Силы<br>" +
            "Урон безоружного удара: 1 +модификатор Силы<br>" +
            "Атака рукопашным оружием: 1к20 + бонус мастерства + модификатор Силы (Ловкости для фехтовального оружия)<br>" +
            "Урон рукопашным оружием: Кость оружия + модификатор Силы(Ловкости для фехтовального оружия)<br>" +
            "Атака дальнобойным оружием: 1к20 + бонус мастерства + модификатор Ловкости (Силы для метательного оружия)<br>" +
            "Урон дальнобойным оружием: Кость оружия + модификатор Ловкости (Силы для метательного оружия)<br>" +
            "Атака заклинанием: 1к20 + бонус мастерства + модификатор базовой характеристики заклинания<br>" +
            "Урон заклинания: индивидуален для каждого заклинания<br>" +
            "Сложность спасброска от заклинания: 8 + модификатор базовой характеристики заклинания + бонус мастерства<br>" +
            "Порядок ходов в бою: 1к20 + Инициатива (модификатор Ловкости)<br>" +
            "Спасбросок: 1к20 + модификатор характеристики + бонус мастерства (если у вас есть владение спасброском)<br>" +
            "Пассивная Внимательность: 10 + модификатор Мудрости (Восприятие)<br>" +
            "Стабилизация умирающего: 1к20 + модификатор Мудрости (Медицина) Сл 10<br>" +
            "Применение инструментов: 1к20 + модификатор характеристики (скажет Мастер) + бонус мастерства (Если есть владение инструментом)";
    public static final String CASE_5 = "<table>\n" +
            " <tbody>\n" +
            " <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Простое рукопашное оружие</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Боевой посох</span></td>\n" +
            "        <td colspan=\"1\">1к6 дробящий</td>\n" +
            "        <td colspan=\"1\">2 см.</td>\n" +
            "        <td colspan=\"1\">4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Универсальное (1к8)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Булава</span></td>\n" +
            "        <td colspan=\"1\">1к6 дробящий</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Дубинка</span></td>\n" +
            "        <td colspan=\"1\">1к4 дробящий</td>\n" +
            "        <td colspan=\"1\">1 см.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кинжал</span></td>\n" +
            "        <td colspan=\"1\">1к4 колющий</td>\n" +
            "        <td colspan=\"1\">2 зм.</td>\n" +
            "        <td colspan=\"1\">1 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое, Метательное (дис. 20/60), Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Копье</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">1 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Метательное (дис. 20/60), Универсальное(1к8)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Легкий молот</span></td>\n" +
            "        <td colspan=\"1\">1к4 дробящий</td>\n" +
            "        <td colspan=\"1\">2 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое, Метательное (дис. 20/60)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Метательное копье</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">5 см.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Метательное (дис. 30/120)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Палица</span></td>\n" +
            "        <td colspan=\"1\">1к8 дробящий</td>\n" +
            "        <td colspan=\"1\">2 см.</td>\n" +
            "        <td colspan=\"1\">10 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Ручной топор</span></td>\n" +
            "        <td colspan=\"1\">1к6 рубящий</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое, Метательное (дис. 20/60)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Серп</span></td>\n" +
            "        <td colspan=\"1\">1к4 рубящий</td>\n" +
            "        <td colspan=\"1\">1 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Простое дальнобойное оружие</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Арбалет, легкий</span></td>\n" +
            "        <td colspan=\"1\">1к8 колющий</td>\n" +
            "        <td colspan=\"1\">25 зм.</td>\n" +
            "        <td colspan=\"1\">5 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 80/320), Двуручное, Перезарядка</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Дротик</span></td>\n" +
            "        <td colspan=\"1\">1к4 колющий</td>\n" +
            "        <td colspan=\"1\">5 мм.</td>\n" +
            "        <td colspan=\"1\">1/4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Метательное (дис. 20/60), Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Короткий лук</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">25 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 80/320), Двуручное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Праща</span></td>\n" +
            "        <td colspan=\"1\">1к4 дробящий</td>\n" +
            "        <td colspan=\"1\">1 см.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 30/120)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Воинское рукопашное оружие</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Алебарда</span></td>\n" +
            "        <td colspan=\"1\">1к10 рубящий</td>\n" +
            "        <td colspan=\"1\">20 зм.</td>\n" +
            "        <td colspan=\"1\">6 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Досягаемость, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Боевая кирка</span></td>\n" +
            "        <td colspan=\"1\">1к8 колющий</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Боевой молот</span></td>\n" +
            "        <td colspan=\"1\">1к8 дробящий</td>\n" +
            "        <td colspan=\"1\">15 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Универсальное (1к10)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Боевой топор</span></td>\n" +
            "        <td colspan=\"1\">1к8 рубящий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Универсальное (1к10)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Глефа</span></td>\n" +
            "        <td colspan=\"1\">1к10 рубящий</td>\n" +
            "        <td colspan=\"1\">20 зм.</td>\n" +
            "        <td colspan=\"1\">6 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Досягаемость, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Двуручный меч</span></td>\n" +
            "        <td colspan=\"1\">2к6 рубящий</td>\n" +
            "        <td colspan=\"1\">50 зм.</td>\n" +
            "        <td colspan=\"1\">6 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Длинное копье</span></td>\n" +
            "        <td colspan=\"1\">1к12 колющий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">6 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Досягаемость, Особое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Длинный меч</span></td>\n" +
            "        <td colspan=\"1\">1к8 рубящий</td>\n" +
            "        <td colspan=\"1\">15 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Универсальное (1к10)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кнут</span></td>\n" +
            "        <td colspan=\"1\">1к4 рубящий</td>\n" +
            "        <td colspan=\"1\">2 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Досягаемость, Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Короткий меч</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое, Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Молот</span></td>\n" +
            "        <td colspan=\"1\">2к6 дробящий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">10 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Моргенштерн</span></td>\n" +
            "        <td colspan=\"1\">1к8 колющий</td>\n" +
            "        <td colspan=\"1\">15 зм.</td>\n" +
            "        <td colspan=\"1\">4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Пика</span></td>\n" +
            "        <td colspan=\"1\">1к10 колющий</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">18 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Досягаемость, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Рапира</span></td>\n" +
            "        <td colspan=\"1\">1к8 колющий</td>\n" +
            "        <td colspan=\"1\">25 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Секира</span></td>\n" +
            "        <td colspan=\"1\">1к12 рубящий</td>\n" +
            "        <td colspan=\"1\">30 зм.</td>\n" +
            "        <td colspan=\"1\">7 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Двуручное, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Скимитар</span></td>\n" +
            "        <td colspan=\"1\">1к6 рубящий</td>\n" +
            "        <td colspan=\"1\">25 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Лёгкое, Фехтовальное</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Трезубец</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">4 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Метательное (дис. 20/60), Универсальное (1к8)</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Цеп</span></td>\n" +
            "        <td colspan=\"1\">1к8 дробящий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Воинское дальнобойное оружие</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Арбалет, ручной</span></td>\n" +
            "        <td colspan=\"1\">1к6 колющий</td>\n" +
            "        <td colspan=\"1\">75 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 30/120), Легкое, Перезарядка</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Арбалет, тяжелый</span></td>\n" +
            "        <td colspan=\"1\">1к10 колющий</td>\n" +
            "        <td colspan=\"1\">50 зм.</td>\n" +
            "        <td colspan=\"1\">18 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 100/400), Двуручное, Перезарядка, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Длинный лук</span></td>\n" +
            "        <td colspan=\"1\">1к8 колющий</td>\n" +
            "        <td colspan=\"1\">50 зм.</td>\n" +
            "        <td colspan=\"1\">2 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 150/600), Двуручное, Тяжёлое</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Духовая трубка</span></td>\n" +
            "        <td colspan=\"1\">1 колющий</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">1 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Боеприпас (дис. 25/100), Перезарядка</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Сеть</span></td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "        <td colspan=\"1\">1 зм.</td>\n" +
            "        <td colspan=\"1\">3 фнт.</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Метательное (дис. 5/15), Особое</td>\n" +
            "    </tr>" +
            "</tbody></table>";
    public static final String CASE_6 = "<div><span class=\"cls_012\">Зелья лечения</span></div>\n" +
            "<table>\n" +
            "    <tbody>\n" +
            "    <tr class=\"table_header\">\n" +
            "        <td>Зелье...</td>\n" +
            "        <td>Редкость</td>\n" +
            "        <td>Хитов восст</td>\n" +
            "        <td>Цена</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>лечения</td>\n" +
            "        <td>Обычная</td>\n" +
            "        <td>2к4 + 2</td>\n" +
            "        <td>50 зм</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>большого лечения</td>\n" +
            "        <td>Необычная</td>\n" +
            "        <td>4к4 + 4</td>\n" +
            "        <td>51-250 зм</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>отличного лечения</td>\n" +
            "        <td>Редкая</td>\n" +
            "        <td>8к4 + 8</td>\n" +
            "        <td>251-2500 зм</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>превосходного лечения</td>\n" +
            "        <td>Очень редкая</td>\n" +
            "        <td>10к4 + 20</td>\n" +
            "        <td>2501-25000 зм</td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "</table>\n" +
            "<br>\n" +
            "<div><span class=\"cls_012\">Алхимический огонь.</span><span class=\"cls_013\"> Действ.: метнуть на ≤ 20 футов (д.ат. по сущ.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_013\">или предмету импровизир. ор.). Попал - урон огнём 1к4 в начале</span></div>\n" +
            "<div><span class=\"cls_013\">каждого своего хода. Окончить эфф. - действ.: пров. Лов. Сл10.</span></div>\n" +
            "<div><span class=\"cls_012\">Блок и лебёдка.</span><span class=\"cls_013\"> Поднять вес ×4.</span></div>\n" +
            "<div><span class=\"cls_012\">Верёвка.</span><span class=\"cls_013\"> 2 хита, порвать - пров. Сил Сл17.</span></div>\n" +
            "<div><span class=\"cls_012\">Замок.</span><span class=\"cls_013\"> Вскрыть - пров. Лов. Сл15.</span></div>\n" +
            "<div><span class=\"cls_012\">Зелье лечения.</span><span class=\"cls_013\"> Действ: выпить или дать др. сущ. - восст. 2к4+2 хита.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Калтропы.</span><span\n" +
            "        class=\"cls_013\"> Действ.: рассыпать на 5×5 фт. Все сущ., входящие в эту</span></div>\n" +
            "<div><span class=\"cls_013\">область: спас. Лов. Сл15, останавливаются и кол. урон 1. Пока не</span></div>\n" +
            "<div><span class=\"cls_013\">восстановит ≥1 хит, скор. ↓ на 10 фт. Движение с скор./2 - без спас.</span></div>\n" +
            "<div><span class=\"cls_012\">Кандалы.</span><span\n" +
            "        class=\"cls_013\"> Удерживают сущ. Маленького и Среднего размера, имеют</span></div>\n" +
            "<div><span class=\"cls_013\">15 хитов. Сбежать: пров. Лов. или Сил. (Сл20). Вскрыть: воровские</span></div>\n" +
            "<div><span class=\"cls_013\">инстр. - пров.Лов. Сл15.</span></div>\n" +
            "<div><span class=\"cls_012\">Кислота.</span><span\n" +
            "        class=\"cls_013\"> Действ.: вылить на сущ. в ≤5 фт или метнуть на ≤20 фт,</span></div>\n" +
            "<div><span class=\"cls_013\">(д.ат. по сущ. или предмету импровизир. ор.). Попал - урон кисл. 2к6.</span></div>\n" +
            "<div><span class=\"cls_012\">Колчан.</span><span class=\"cls_013\"> Вмещает 20 стрел.</span></div>\n" +
            "<div><span class=\"cls_012\">Комплект для лазания.</span><span\n" +
            "        class=\"cls_013\"> Действ.: закрепиться на высоте (падение/</span></div>\n" +
            "<div><span class=\"cls_013\">подъем на ≤25 фт. от места крепления) или открепиться.</span></div>\n" +
            "<div><span class=\"cls_012\">Комплект целителя.</span><span\n" +
            "        class=\"cls_013\"> Действ. - -1 исп.: стабилизировать сущ. с 0</span></div>\n" +
            "<div><span class=\"cls_013\">хитов без пров. Мдр(Медицина). (имеет 10 исп.).</span></div>\n" +
            "<div><span class=\"cls_012\">Контейнер для арбалетных болтов.</span><span class=\"cls_013\"> Вмещает 20 болтов.</span></div>\n" +
            "<div><span class=\"cls_012\">Контейнер для карт и свитков.</span><span class=\"cls_013\"> Хранит: бумага(10л.)/пергамент(5л.)</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Кошель.</span><span class=\"cls_013\"> Хранит 20 снарядов пращи/50 иголок духовой трубки, и т.д.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Лампа.</span><span class=\"cls_013\"> Ярк.св. 15 фт, тускл.св. ещё 30 фт. 6 ч. от 0,5л. масла (1фляга)</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Ломик.</span><span class=\"cls_013\"> Если рычаг поможет - пров. Сил. с преим.</span></div>\n" +
            "<div><span class=\"cls_012\">Масло.</span><span class=\"cls_013\"> 1 фляга (0,5 литра). Действ.: облить сущ. в ≤5 фт, или метнуть</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_013\">на ≤20 фт, (д.ат. по сущ. или предмету импровизир. ор.). Попал - в</span></div>\n" +
            "<div><span class=\"cls_013\">течении 1 мин. если цель получает урон огнём, то получает доп. урон</span></div>\n" +
            "<div><span class=\"cls_013\">огнём 5. Или можно вылить масло на землю 5×5 фт. (если пол ровный).</span></div>\n" +
            "<div><span class=\"cls_013\">Если масло поджечь - горит 2 раунда, причиняя урон огнём 5 всем</span></div>\n" +
            "<div><span class=\"cls_013\">сущ., входящим в эту область или оканчивающим в ней ход (1/ход).</span></div>\n" +
            "<div><span class=\"cls_012\">Металлические шарики.</span><span\n" +
            "        class=\"cls_013\"> Действ.: рассыпать на 10×10 фт. Сущ.,</span></div>\n" +
            "<div><span class=\"cls_013\">перемещающиеся по этой области: спас. Лов. Сл10, провал - падает</span></div>\n" +
            "<div><span class=\"cls_013\">ничком. Движение с скор./2 - без спас.</span></div>\n" +
            "<div><span class=\"cls_012\">Охотничий капкан.</span><span\n" +
            "        class=\"cls_013\"> Действ.: установить ловушку - крепится толстой</span></div>\n" +
            "<div><span class=\"cls_013\">цепью к неподвижн. предмету. Наступившее сущ. -спас. Лов. Сл13,</span></div>\n" +
            "<div><span class=\"cls_013\">провал - кол. урон 1к4 и прекращает движ. Пока не освободится -</span></div>\n" +
            "<div><span class=\"cls_013\">перемещения ограничены длиной цепи (≈1м.). Любое сущ. - действ.:</span></div>\n" +
            "<div><span class=\"cls_013\">высвободить себя или др.сущ. (пров. Сил. Сл13,). Каждый провал.</span></div>\n" +
            "<div><span class=\"cls_013\">пров. - кол. урон 1 по пойманному.</span></div>\n" +
            "<div><span class=\"cls_012\">Противоядие.</span><span class=\"cls_013\"> 1 час - спас. от яда с преим. (не нежить, не конструкт)</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Свеча.</span><span class=\"cls_013\"> Ярк.св. 5 фт, тускл.св. ещё 5 фт. (1 ч.)</span></div>\n" +
            "<div><span class=\"cls_012\">Столовый набор:</span><span\n" +
            "        class=\"cls_013\"> чашка, столовые приборы, сковорода, тарелка</span></div>\n" +
            "<div><span class=\"cls_012\">Святая вода.</span><span class=\"cls_013\"> Действ.: облить сущ. в ≤5 фт, или метнуть на ≤20 фт,</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_013\">(д.ат. по сущ. или предмету импровизир. ор.). Попал - если цель изверг</span></div>\n" +
            "<div><span class=\"cls_013\">или нежить - урон излуч. 2к6. Жрец или паладин: создать святую воду</span></div>\n" +
            "<div><span class=\"cls_013\">- ритуал: 1 час, толчёное серебро (25 зм), яч.закл.1 ур.</span></div>\n" +
            "<div><span class=\"cls_012\">Таран, портативный.</span><span\n" +
            "        class=\"cls_013\"> +4 к пров. Сил. на вышибание дверей. Если</span></div>\n" +
            "<div><span class=\"cls_013\">помогают - с преим.</span></div>\n" +
            "<div><span class=\"cls_012\">Трутница.</span><span\n" +
            "        class=\"cls_013\"> Розжиг факела — 1 действ. Розжиг др. огня - 1 мин.</span></div>\n" +
            "<div><span class=\"cls_012\">Увеличительное стекло.</span><span\n" +
            "        class=\"cls_013\"> Розжиг огня - свет солнца, трут, 5 мин.</span></div>\n" +
            "<div><span class=\"cls_013\">Пров. хар-к для оценки или исслед. мелких предметов -с преим.</span></div>\n" +
            "<div><span class=\"cls_012\">Факел.</span><span class=\"cls_013\"> Ярк.св. 20 фт, тускл.св. ещё 20 фт. (1 ч.). Р.ат. - урон огнём 1.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_012\">Фонарь, закрытый.</span><span class=\"cls_013\"> Ярк.св. 30 фт, тускл.св. ещё 30 фт. (6 ч. от 0,5л.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_013\">масла (1фляга)). Действ.: опустить козырёк - только тускл.св. 5 фт.</span></div>\n" +
            "<div><span class=\"cls_012\">Фонарь, направленный.</span><span\n" +
            "        class=\"cls_013\"> Ярк.св. 60 фт. конус, тускл.св. ещё 60 фт.</span></div>\n" +
            "<div><span class=\"cls_013\">(6 ч. от 0,5л. масла (1фляга)).</span></div>\n" +
            "<div><span class=\"cls_012\">Цепь.</span><span class=\"cls_013\"> 10 хитов, порвать - пров. Сил. Сл20.</span></div>\n" +
            "<div><span class=\"cls_012\">Яд, простой.</span><span class=\"cls_013\"> Действ: покрыть ядом 1 руб. или кол. ор. или 3 боепр.</span>\n" +
            "</div>\n" +
            "<div><span class=\"cls_013\">Яд активен 1 мин. Цель отравленного ор. или боепр. - спас. Тел Сл10.</span></div>\n" +
            "<div><span class=\"cls_013\">Провал - урон ядом 1к4.</span></div>";
    public static final String CASE_4 = "<table>\n" +
            "    <tbody>" +
            "    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Лёгкий доспех</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Стеганый</span></td>\n" +
            "        <td colspan=\"1\">11 + модификатор ЛОВ</td>\n" +
            "        <td colspan=\"1\">5 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кожаный</span></td>\n" +
            "        <td colspan=\"1\">11 + модификатор ЛОВ</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Проклёпанный кожаный</span></td>\n" +
            "        <td colspan=\"1\">12 + модификатор ЛОВ</td>\n" +
            "        <td colspan=\"1\">45 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Средний доспех</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Шкурный</span></td>\n" +
            "        <td colspan=\"1\">12 + модификатор ЛОВ (макс. 2)</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кольчужная рубаха</span></td>\n" +
            "        <td colspan=\"1\">13 + модификатор ЛОВ (макс. 2)</td>\n" +
            "        <td colspan=\"1\">50 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Чешуйчатый</span></td>\n" +
            "        <td colspan=\"1\">14 + модификатор ЛОВ (макс. 2)</td>\n" +
            "        <td colspan=\"1\">50 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кираса</span></td>\n" +
            "        <td colspan=\"1\">14 + модификатор ЛОВ (макс. 2)</td>\n" +
            "        <td colspan=\"1\">400 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Полулаты</span></td>\n" +
            "        <td colspan=\"1\">15 + модификатор ЛОВ (макс. 2)</td>\n" +
            "        <td colspan=\"1\">750 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Тяжелый доспех</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кольчатый</span></td>\n" +
            "        <td colspan=\"1\">14</td>\n" +
            "        <td colspan=\"1\">30 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Кольчуга</span></td>\n" +
            "        <td colspan=\"1\">16</td>\n" +
            "        <td colspan=\"1\">75 зм.</td>\n" +
            "        <td colspan=\"1\">сила 13</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Наборный</span></td>\n" +
            "        <td colspan=\"1\">17</td>\n" +
            "        <td colspan=\"1\">200 зм.</td>\n" +
            "        <td colspan=\"1\">сила 15</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Латы</span></td>\n" +
            "        <td colspan=\"1\">18</td>\n" +
            "        <td colspan=\"1\">1 500 зм.</td>\n" +
            "        <td colspan=\"1\">сила 15</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">Помеха</td>\n" +
            "    </tr>    <tr>\n" +
            "        <td colspan=\"4\"><strong><em><center>Щиты</center></em></strong></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"1\" rowspan=\"2\"><span class=\"cls_012\">Щит</span></td>\n" +
            "        <td colspan=\"1\">+2</td>\n" +
            "        <td colspan=\"1\">10 зм.</td>\n" +
            "        <td colspan=\"1\">-</td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"3\">-</td>\n" +
            "    </tr>" +
            "    </tbody></table>";
    private final List<String> data = Arrays.asList("Сражение", "Состояния", "Виды урона", "Укрытие", "Броня", "Оружие", "Снаряжение", "Формулы", "Огнестрельное оружие и взрывчатка");

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lv = getListView();
        ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.color.colorPrimary));
        lv.setDivider(sage);
        lv.setDividerHeight(1);
        SingleAdapter adapter = new SingleAdapter(getActivity(), data);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0:
                Intent intent = new Intent(getContext(), MasterActionActivity.class);
                startActivityForResult(intent, 0);

                break;
            case 1:
                intent = new Intent(getContext(), HelpActivity.class);

                ArrayList<CustomItem> m2 = new ArrayList<>();
                m2.add(new CustomItem("Безсознательный", "Находящееся без сознания существо«недееспособно», не способно перемещаться и говорить, а также не осознаёт своё окружение.\n• Существо роняет всё, что держит, и падаетничком.\n• Существо автоматически проваливаетспасброски Силы и Ловкости."));
                m2.add(new CustomItem("Испуганный", "Испуганное существо совершает с помехойпроверки характеристик и броски атаки, пока источник его страха находится в пределах его линии обзора.\n• Существо не способно добровольноприблизиться к источнику своего страха"));
                m2.add(new CustomItem("Невидимый", "Невидимое существо невозможно увидеть безпомощи магии или особого чувства.  Местонахождение существа можно определить по шуму, который оно издаёт, или по оставленным им следам.\n• Броски атаки по невидимому существусовершаются с помехой, а его броски атаки с преимуществом."));
                m2.add(new CustomItem("Недееспособный", "Недееспособное существо не может совершатьдействия и реакции"));
                m2.add(new CustomItem("Оглохший", "Оглохшее существо ничего не слышит иавтоматически проваливает все проверкихарактеристик, связанные со слухом"));
                m2.add(new CustomItem("Опутанный", "Скорость опутанного существа равна 0, и ононе получает выгоды ни от каких бонусов кскорости.\n• Броски атаки по такому существу совершаютсяс преимуществом, а его броски атаки спомехой.\n• Существо совершает с помехой спасброскиЛовкости"));
                m2.add(new CustomItem("Схваченный", "Скорость схваченного существа равна 0.\n• Состояние оканчивается, если схватившийстановится недееспособен.\n• Это состояние также оканчивается, если какойлибо эффект выводит схваченное существо иззоны досягаемости того, кто его удерживает."));
                m2.add(new CustomItem("Окаманевший", "Существо превращается в камень.\n• Существо «недееспособно», не способнодвигаться и говорить, а также не осознаёт своё окружение.\n• Броски атаки по существу совершаются спреимуществом.\n• Существо автоматически проваливаетспасброски Силы и Ловкости.\n• Существо получает сопротивление ко всемвидам урона, ядам и болезням"));
                m2.add(new CustomItem("Ослеплённый", "Ослеплённое существо ничего не видит иавтоматически проваливает все проверкихарактеристик, связанные со зрением.\n• Броски атаки по такому существу совершаются спреимуществом, а его броски атаки совершаются с помехой"));
                m2.add(new CustomItem("Очарованный", "Очарованное существо не может атаковатьтого, кто его очаровал, а также делать егоцелью умения или магического эффекта,причиняющего вред.\n• Искуситель совершает с преимуществом всепроверки характеристик при социальномвзаимодействии с очарованным существом."));
                m2.add(new CustomItem("Ошеломлённый/парализованный", "Существо «недееспособно», не способноперемещаться.\n• Существо автоматически проваливаетспасброски Силы и Ловкости.\n• Броски атаки по существу совершаются спреимуществом"));
                m2.add(new CustomItem("Сбитый с ног", "Сбитое с ног существо способно перемещатьсятолько ползком, пока не встанет, прервав темсамым это состояние.\n• Существо совершает с помехой броски атаки.\n• Броски атаки по существу совершаются спреимуществом, если нападающий находится впределах 5 футов от него. В противном случаеброски атаки совершаются с помехой"));
                m2.add(new CustomItem("Отравленный", "Отравленное существо совершает с помехойброски атаки и проверки характеристик."));

                intent.putExtra("CustomItems", m2);
                intent.putExtra("title", "Состояния");

                startActivityForResult(intent, 0);

                break;
            case 2:
                intent = new Intent(getContext(), HelpActivity.class);

                ArrayList<CustomItem> m3 = new ArrayList<>();
                m3.add(new CustomItem("Дробящий", "Тяжёлые силовые атаки — молотом, падением, сдавливанием и т. п. — причиняют дробящий урон"));
                m3.add(new CustomItem("Звук", "Оглушительные звуковые волны, такие как от заклинания волна грома, причиняют урон звуком"));
                m3.add(new CustomItem("Излучение", "Урон излучением, причиняемый заклинанием небесный огонь жреца и карающим оружием ангела, опаляют плоть как огонь и переполняют дух силой"));
                m3.add(new CustomItem("Кислота", "Едкое дыхание чёрного дракона и растворяющая слизь чёрного пудинга причиняют урон кислотой"));
                m3.add(new CustomItem("Колющий", "Колющие и пронзающие атаки, включая удары копьём и укусы чудовищ, причиняют колющий урон"));
                m3.add(new CustomItem("Некротическая энергия.", "Некротическая энергия, излучаемая некоторой нежитью и такими заклинаниями как леденящее прикосновение, иссушают плоть и даже душу"));
                m3.add(new CustomItem("Огонь", "Красный дракон, выдыхающий пламя, и многие заклинания, создающие жар, причиняют урон огнём"));
                m3.add(new CustomItem("Психическая энергия", "Атаки силой разума, такие как у иллитидов, причиняют урон психической энергией."));
                m3.add(new CustomItem("Рубящий", "Мечи, топоры и когти чудовищ причиняют рубящий урон"));
                m3.add(new CustomItem("Силовое поле", "Силовое поле это чистая магия, сфокусированная в разрушительную силу. Чаще всего силовым полем причиняют урон заклинания, такие как волшебная стрела и божественное оружие"));
                m3.add(new CustomItem("Холод", "Лютый холод от копья ледяного дьявола и морозное дыхание белого дракона причиняют урон холодом"));
                m3.add(new CustomItem("Электричество", "Заклинание молния и дыханиесинего дракона причиняют урон электричеством"));
                m3.add(new CustomItem("Яд", "Ядовитые укусы и токсичное дыхание зелёного дракона причиняют урон ядом"));

                intent.putExtra("CustomItems", m3);
                intent.putExtra("title", "Виды урона");

                startActivityForResult(intent, 0);
                break;
            case 3:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m4 = new ArrayList<>();

                m4.add(new CustomItem("Укрытие на 1/2", "Бонус к КД +2"));
                m4.add(new CustomItem("Укрытие на 3/4", "Бонус к КД +5"));
                m4.add(new CustomItem("Полное укрытие ", "При полном укрытии вас нельзя атаковать и накладывать на васзаклинания."));

                intent.putExtra("CustomItems", m4);
                intent.putExtra("title", "Укрытие");

                startActivityForResult(intent, 0);

                break;
            case 4:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m5 = new ArrayList<>();

                m5.add(new CustomItem("", CASE_4));

                intent.putExtra("CustomItems", m5);
                intent.putExtra("title", "Броня");

                startActivityForResult(intent, 0);

                break;

            case 5:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m6 = new ArrayList<>();

                m6.add(new CustomItem("", CASE_5));

                intent.putExtra("CustomItems", m6);
                intent.putExtra("title", "Оружие");

                startActivityForResult(intent, 0);

                break;

            case 6:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m7 = new ArrayList<>();
                m7.add(new CustomItem("", CASE_6));

                intent.putExtra("CustomItems", m7);
                intent.putExtra("title", "Снаряжение");

                startActivityForResult(intent, 0);

                break;
            case 7:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m8 = new ArrayList<>();
                for (String s1 : CASE_7.split("<br>")) {
                    String[] ss1 = s1.split(":");
                    m8.add(new CustomItem(ss1[0], ss1[1]));

                }
                intent.putExtra("CustomItems", m8);
                intent.putExtra("title", "Формулы");

                startActivityForResult(intent, 0);

                break;
            case 8:
                intent = new Intent(getContext(), HelpActivity.class);
                ArrayList<CustomItem> m9 = new ArrayList<>();
                m9.add(new CustomItem("", "<div class=\"desc card__article-body\" itemprop=\"articleBody\">\n" +
                        "    <h3 class=\"underlined\">Огнестрельное оружие<br></h3><p>Если хотите передать бесшабашный дух Трёх Мушкетёров и подобных романов, можете ввести в свою кампанию в стиле эпохи Возрождения пороховое оружие. То же самое касается кампаний, в которых разбились космические корабли или присутствуют элементы современной Земли. В таблице «огнестрельное оружие» приводятся примеры для всех этих трёх периодов. Для современного и футуристичного оружия цены не указаны.</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">ВЛАДЕНИЕ </h3><p>Вы сами решаете, владеет ли персонаж обращением с огнестрельным оружием. У персонажей в большинстве миров таких навыков нет. Во время простоя персонажи могут использовать правила обучения из Книги игрока, чтобы получить владение этим навыком, при условии, что у них есть достаточное количество боеприпасов, чтобы оружие можно было использовать во время обучения.</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">СВОЙСТВА </h3><p>Огнестрельное оружие использует особые боеприпасы, и у некоторых из них есть свойства «боекомплект» и «очередь».</p>\n" +
                        "    <p><strong><em>Боекомплект.</em></strong> Из оружия с этим свойством можно совершить ограниченное число выстрелов. После этого персонаж должен перезарядить его действием или бонусным действием (на свой выбор).</p>\n" +
                        "    <p><strong><em>Боеприпас.</em></strong> Боеприпасы огнестрельного оружия уничтожаются при использовании. Современное оружие и оружие эпохи Возрождения использует пули и патроны. Футуристическое оружие использует особые боеприпасы, называемые батареями. В одной батарее достаточно энергии, чтобы сделать все выстрелы, которые способно совершить оружие.</p>\n" +
                        "    <p><strong><em>Очередь.</em></strong> Оружие с этим свойством может совершать как обычные атаки по одной цели, так и атаки по области 10-футового куба в пределах обычной дистанции. Все существа в этой области должны совершить спасбросок Ловкости Сл 15, иначе они получат обычный урон этого оружия. Такое действие использует десять боеприпасов.</p>\n" +
                        "    <h4 class=\"tableTitle\">ОГНЕСТРЕЛЬНОЕ ОРУЖИЕ</h4>\n" +
                        "    <div class=\"table-wrapper\"><table>\n" +
                        "        <tbody>\n" +
                        "        <tr class=\"table_header\"><td>Предмет</td>\n" +
                        "            <td>Стоимость</td>\n" +
                        "            <td>Урон</td>\n" +
                        "            <td>Вес</td>\n" +
                        "            <td>Свойства</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><strong>эпоха Возрождения</strong></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Воинское дальнобойное оружие</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Пистоль</td>\n" +
                        "            <td>250 зм</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>1к10</span><span></span></span> колющий</td>\n" +
                        "            <td>3 фнт.</td>\n" +
                        "            <td>Боеприпас (дис. 30/90), перезарядка</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Мушкет</td>\n" +
                        "            <td>500 зм</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>1к12</span><span></span></span> колющий</td>\n" +
                        "            <td>10 фнт.</td>\n" +
                        "            <td>Боеприпас (дис. 40/120), <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span>, перезарядка</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Боеприпасы</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Пули (10)</td>\n" +
                        "            <td>3 зм</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>2 фнт.</td>\n" +
                        "            <td>-</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><strong>Современное</strong></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Воинское дальнобойное оружие</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Пистолет, автоматический</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>2к6</span><span></span></span> колющий</td>\n" +
                        "            <td>3 фнт.</td>\n" +
                        "            <td>Боекомплект (15 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 50/150)</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Револьвер</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>2к8</span><span></span></span> колющий</td>\n" +
                        "            <td>3 фнт.</td>\n" +
                        "            <td>Боекомплект (6 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 40/120)</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Винтовка, охотничья</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>2к10</span><span></span></span> колющий</td>\n" +
                        "            <td>8 фнт.</td>\n" +
                        "            <td>Боекомплект (5 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 80/240)</span>, <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Винтовка, автоматическая</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>2к8</span><span></span></span> колющий</td>\n" +
                        "            <td>8 фнт.</td>\n" +
                        "            <td>Боекомплект (30 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 80/240)</span>, <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span>, очередь</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Дробовик</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>2к8</span><span></span></span> колющий</td>\n" +
                        "            <td>7 фнт.</td>\n" +
                        "            <td>Боекомплект (2 выстрела), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 30/90)</span>, <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Боеприпасы</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Патроны (10)</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>1 фнт.</td>\n" +
                        "            <td>-</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><strong>Футуристическое</strong></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Воинское дальнобойное оружие</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Лазерный пистолет</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>3к6</span><span></span></span> излучение</td>\n" +
                        "            <td>2 фнт.</td>\n" +
                        "            <td>Боекомплект (50 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 40/120)</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Винтовка на антиматерии</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>6к8</span><span></span></span> некротическая энергия</td>\n" +
                        "            <td>10 фнт.</td>\n" +
                        "            <td>Боекомплект (2 выстрела), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 120/360)</span>, <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Лазерная винтовка</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td><span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>3к8</span><span></span></span> излучение</td>\n" +
                        "            <td>7 фнт.</td>\n" +
                        "            <td>Боекомплект (30 выстрелов), <span tooltip-for=\"weapon.ammo\" class=\"tooltipstered tooltip\">боеприпас (дис. 100/300)</span>, <span tooltip-for=\"weapon.twohanded\" class=\"tooltipstered tooltip\">двуручное</span></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"5\"><em>Боеприпасы</em></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Батарея</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>5 унц.</td>\n" +
                        "            <td>-</td>\n" +
                        "        </tr>\n" +
                        "        </tbody>\n" +
                        "    </table></div>\n" +
                        "    <br><h3 class=\"underlined\">ВЗРЫВЧАТКА </h3><p>В кампании могут встречаться взрывчатые вещества из эпохи Возрождения или современного мира (для последних цены не указаны), представленные в таблице «взрывчатка».</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">БОМБА </h3><p>Персонаж может действием поджечь бомбу и бросить её на расстояние до 60 футов. Все существа в пределах 5 футов от этой точки должны преуспеть в спасброске Ловкости Сл 12, иначе они получат урон огнём <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>3к6</span><span></span></span>.</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">ПОРОХ </h3><p>Порох преимущественно используется для приведения в движение пули в стволе пистолета или винтовки, а также для изготовления бомб. Порох продаётся в маленьких деревянных бочонках или водонепроницаемых рожках.</p>\n" +
                        "    <p>Поджигание контейнера с порохом может вызвать взрыв, причиняющий урон огнём всем существам в пределах 10 футов от него (<span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>3к6</span><span></span></span> от рожка, <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>7к6</span><span></span></span> от бочонка). Успешный спасбросок Ловкости Сл 12 уменьшает урон вдвое. Подожжённая унция пороха просто горит 1 раунд, распространяя яркий свет в радиусе 30 футов и тусклый свет в пределах ещё 30 футов.</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">ДИНАМИТ </h3><p>Существо может действием поджечь динамитную шашку и бросить её на расстояние до 60 футов. Все существа в пределах 5 футов от этой точки должны совершить спасбросок Ловкости Сл 12, получая дробящий урон <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>3к6</span><span></span></span> при провале или половину этого урона при успехе.</p>\n" +
                        "    <p>Персонаж может связать вместе несколько динамитных шашек, чтоб они взорвались одновременно. Каждая добавленная шашка увеличивает урон на <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>1к6</span><span></span></span> (максимум — <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>10к6</span><span></span></span>), а радиус взрыва — на 5 футов (максимум — 20 футов).</p>\n" +
                        "    <p>Динамит может быть переделан на удлинённый фитиль, чтоб взорваться через определённое время, обычно от 1 до 6 раундов. Совершите проверку инициативы за динамит. После того, как пройдёт указанное количество раундов, динамит взорвётся при своей инициативе.</p>\n" +
                        "    <br><h3 class=\"smallSectionTitle\">ГРАНАТЫ </h3><p>Персонаж может действием кинуть гранату на расстояние до 60 футов. Имея гранатомёт, можно запустить её на расстояние до 120 футов.</p>\n" +
                        "    <p>Все существа в пределах 20 футов от места взрыва осколочной гранаты должны совершить спасбросок Ловкости Сл 15, получая при провале колющий урон <span class=\"dice\" onclick=\"return ROLLtABLE.rollDice(this);\"><span>5к6</span><span></span></span> или половину этого урона при успехе.</p>\n" +
                        "    <p>Спустя раунд после приземления дымовой гранаты появляется облако дыма, создающее сильно заслонённую местность с радиусом 20 футов. Умеренный ветер (по крайней мере, 10 миль в час) разгоняет дым за 4 раунда, сильный ветер (20 или больше миль в час) разгоняет его за 1 раунд.</p>\n" +
                        "    <h4 class=\"tableTitle\">ВЗРЫВЧАТКА</h4>\n" +
                        "    <div class=\"table-wrapper\"><table>\n" +
                        "        <tbody>\n" +
                        "        <tr class=\"table_header\"><td>Предмет</td>\n" +
                        "            <td>Стоимость</td>\n" +
                        "            <td>Вес</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"3\"><strong>эпоха Возрождения</strong></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Бомба</td>\n" +
                        "            <td>150 зм</td>\n" +
                        "            <td>1 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Порох, бочонок</td>\n" +
                        "            <td>250 зм</td>\n" +
                        "            <td>20 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Порох, рожок</td>\n" +
                        "            <td>35 зм</td>\n" +
                        "            <td>2 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td colspan=\"3\"><strong>современное</strong></td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Граната, дымовая</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>2 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Граната, осколочная</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>1 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Гранатомёт</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>7 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td class=\"pad\">Динамит (шашка)</td>\n" +
                        "            <td>-</td>\n" +
                        "            <td>1 фнт.</td>\n" +
                        "        </tr>\n" +
                        "        </tbody>\n" +
                        "    </table></div>\n" +
                        "\n" +
                        "</div>"));

                intent.putExtra("CustomItems", m9);
                intent.putExtra("title", "Огнестрельное оружие и взрывчатка");

                startActivityForResult(intent, 0);

                break;
        }
    }
}
