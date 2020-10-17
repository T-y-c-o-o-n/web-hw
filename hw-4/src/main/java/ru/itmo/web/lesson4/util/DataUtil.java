package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.User.Color;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.RED),
            new User(6, "pashka", "Pavel Mavrin", Color.BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Priority Queue", "Приоритетная очередь (англ. priority queue) — это абстрактная структура данных наподобие стека или очереди, где у каждого элемента есть приоритет. Элемент с более высоким приоритетом находится перед элементом с более низким приоритетом. Если у элементов одинаковые приоритеты, они располагаются в зависимости от своей позиции в очереди. Обычно приоритетные очереди реализуются с помощью куч (англ. heap).\n" +
                    "\n" +
                    "Содержание\n" +
                    "1\tОперации\n" +
                    "2\tРеализации\n" +
                    "2.1\tНаивная\n" +
                    "2.2\tОбычная\n" +
                    "3\tВиды приоритетных очередей\n" +
                    "4\tПрименение\n" +
                    "5\tРеализации в языках программирования\n" +
                    "6\tСм. также\n" +
                    "7\tПримечания\n" +
                    "8\tИсточники информации\n" +
                    "Операции\n" +
                    "Приоритетные очереди поддерживают следующие операции:\n" +
                    "\n" +
                    "findMin или findMax — поиск элемента с наибольшим приоритетом,\n" +
                    "insert или push — вставка нового элемента,\n" +
                    "extractMin или extractMax — извлечь элемент с наибольшим приоритетом,\n" +
                    "deleteMin или deleteMax — удалить элемент с наибольшим приоритетом,\n" +
                    "increaseKey или decreaseKey — обновить значение элемента,\n" +
                    "merge — объединение двух приоритетных очередей, сохраняя оригинальные очереди,\n" +
                    "meld — объединение двух приоритетных очередей, разрушая оригинальные очереди,\n" +
                    "split — разбить приоритную очередь на две части.", 9),
            new Post(2, "Codeforces Round 664", "Hi!\n" +
                    "\n" +
                    "On суббота, 10 октября 2020 г. в 17:50 we will host Codeforces Global Round 11.\n" +
                    "\n" +
                    "This is the fifth round of the 2020 series of Codeforces Global Rounds. The rounds are open and rated for everybody.\n" +
                    "\n" +
                    "The prizes for this round are as follows:\n" +
                    "\n" +
                    "30 best participants get a t-shirt.\n" +
                    "20 t-shirts are randomly distributed among those with ranks between 31 and 500, inclusive.\n" +
                    "The prizes for the 6-round series in 2020:\n" +
                    "\n" +
                    "In each round top-100 participants get points according to the table.\n" +
                    "The final result for each participant is equal to the sum of points he gets in the four rounds he placed the highest.\n" +
                    "The best 20 participants over all series get sweatshirts and place certificates.\n" +
                    "Thanks to XTX, which in 2020 supported the global rounds initiative!\n" +
                    "\n" +
                    "Problems for this round are set by me. Thanks a lot to the coordinator antontrygubO_o, to the testers dacin21, Giada, H4CKOM, DimmyT, postscript, oolimry, RedDreamer, PM11, Tlatoani, growup974, nvmdava, bhaskarjoshi2001, dorijanlendvaj, and to MikeMirzayanov for the Codeforces and Polygon platforms.\n" +
                    "\n" +
                    "The round will have 8 problems and will last 180 minutes.\n" +
                    "\n" +
                    "The (unusual) scoring distribution is: 500-750-1000-1000-1500-2250-2250-4500.\n" +
                    "\n" +
                    "Why such a scoring distribution?\n" +
                    "I hope you will have fun solving the problems!\n" +
                    "\n" +
                    "UPD: The round is postponed by 15 minutes because just before the round there will be a 10-minutes-long unrated testing round. Considering the recent Codeforces downtime, this is a measure to make sure that there will not be technical issues during the real round.\n" +
                    "\n" +
                    "UPD2: There were no technical issues during the testing round, hence the real round will happen. Good luck and see you in the scoreboard!\n" +
                    "\n" +
                    "UPD3: I hope you liked the problems, here is the editorial.\n" +
                    "\n" +
                    "UPD4: Congratulations to the winners!\n" +
                    "\n" +
                    "Benq\n" +
                    "yosupo\n" +
                    "ksun48\n" +
                    "Um_nik\n" +
                    "ecnerwala\n" +
                    "apiadu\n" +
                    "maroonrk\n" +
                    "zscoder\n" +
                    "SirShokoladina\n" +
                    "gamegame\n" +
                    "UPD5: And congratulation to Petr who upsolved H before the editorial was posted! You made me happy!", 6),
            new Post(5, "Changes in next rounds", "Вскоре будут изменения в следующих раундах. Следите за постами!", 1),
            new Post(10, "Segment Tree", "Дерево отрезков\n" +
                    "Дерево отрезков — это структура данных, которая позволяет эффективно (т.е. за асимптотику O (\\log n)) реализовать операции следующего вида: нахождение суммы/минимума элементов массива в заданном отрезке (a[l \\ldots r], где l и r поступают на вход алгоритма), при этом дополнительно возможно изменение элементов массива: как изменение значения одного элемента, так и изменение элементов на целом подотрезке массива (т.е. разрешается присвоить всем элементам a[l \\ldots r] какое-либо значение, либо прибавить ко всем элементам массива какое-либо число).\n" +
                    "\n" +
                    "Вообще, дерево отрезков — очень гибкая структура, и число задач, решаемых ей, теоретически неограниченно. Помимо приведённых выше видов операций с деревьями отрезков, также возможны и гораздо более сложные операции (см. раздел \"Усложнённые версии дерева отрезков\"). В частности, дерево отрезков легко обобщается на большие размерности: например, для решения задачи о поиске суммы/минимума в некотором подпрямоугольнике данной матрицы (правда, уже только за время O (\\log^2 n)).\n" +
                    "\n" +
                    "Важной особенностью деревьев отрезков является то, что они потребляют линейный объём памяти: стандартному дереву отрезков требуется порядка 4n элементов памяти для работы над массивом размера n.\n" +
                    "\n" +
                    "Описание дерева отрезков в базовом варианте\n" +
                    "Для начала рассмотрим простейший случай дерева отрезков — дерево отрезков для сумм. Если ставить задачу формально, то у нас есть массив a[0..n-1], и наше дерево отрезков должно уметь находить сумму элементов с l-го по r-ый (это запрос суммы), а также обрабатывать изменение значения одного указанного элемента массива, т.е. фактически реагировать на присвоение a[i]=x (это запрос модификации). Ещё раз повторимся, дерево отрезков должно обрабатывать оба этих запроса за время O (\\log n).\n" +
                    "\n" +
                    "Структура дерева отрезков\n" +
                    "Итак, что же представляет из себя дерево отрезков?\n" +
                    "\n" +
                    "Подсчитаем и запомним где-нибудь сумму элементов всего массива, т.е. отрезка a[0 \\ldots n-1]. Также посчитаем сумму на двух половинках этого массива: a[0 \\ldots n/2] и a[n/2+1 \\ldots n-1]. Каждую из этих двух половинок в свою очередь разобьём пополам, посчитаем и сохраним сумму на них, потом снова разобьём пополам, и так далее, пока текущий отрезок не достигнет длины 1. Иными словами, мы стартуем с отрезка [0;n-1] и каждый раз делим текущий отрезок надвое (если он ещё не стал отрезком единичной длины), вызывая затем эту же процедуру от обеих половинок; для каждого такого отрезка мы храним сумму чисел на нём.\n" +
                    "\n" +
                    "Можно говорить, что эти отрезки, на которых мы считали сумму, образуют дерево: корень этого дерева — отрезок [0 \\ldots n-1], а каждая вершина имеет ровно двух сыновей (кроме вершин-листьев, у которых отрезок имеет длину 1). Отсюда и происходит название — \"дерево отрезков\" (хотя при реализации обычно никакого дерева явно не строится, но об этом ниже в разделе реализации).\n" +
                    "\n" +
                    "Итак, мы описали структуру дерева отрезков. Сразу заметим, что оно имеет линейный размер, а именно, содержит менее 2n вершин. Понять это можно следующим образом: первый уровень дерева отрезков содержит одну вершину (отрезок [0 \\ldots n-1]), второй уровень — в худшем случае две вершины, на третьем уровне в худшем случае будет четыре вершины, и так далее, пока число вершин не достигнет n. Таким образом, число вершин в худшем случае оценивается суммой n + n/2 + n/4 + n/8 + \\ldots + 1 < 2n.\n" +
                    "\n" +
                    "Стоит отметить, что при n, отличных от степеней двойки, не все уровни дерева отрезков будут полностью заполнены. Например, при n=3 левый сын корня есть отрезок [0 \\ldots 1], имеющий двух потомков, в то время как правый сын корня — отрезок [2 \\ldots 2], являющийся листом. Никаких особых сложностей при реализации это не составляет, но тем не менее это надо иметь в виду.\n" +
                    "\n" +
                    "Высота дерева отрезков есть величина O (\\log n) — например, потому что длина отрезка в корне дерева равна n, а при переходе на один уровень вниз длина отрезков уменьшается примерно вдвое.", 11),
            new Post(11, "Codeforces Round 665", "Трям, Codeforces!\n" +
                    "\n" +
                    "andersen\n" +
                    "\n" +
                    "Возможно, вы ждете от нас анонс финала чемпионата БГУИР, но пока мы только рады пригласить вас на Codeforces Round #675 (Div. 2), который пройдет воскресенье, 4 октября 2020 г. в 19:05. Этот раунд будет рейтинговым для участников, чей рейтинг ниже 2100.\n" +
                    "\n" +
                    "Задачи для вас кроме меня готовили andrew, hloya_ygrt, AleXman111 и Vladik. Мы думаем, что подготовили хорошие задачи на Andersen Programming Contest 2020. Квалификация. Потом мы отобрали лучшие из них для этого раунда.\n" +
                    "\n" +
                    "Компания Andersen уже второй год проводит соревнование, которое в первую очередь предназначено для поддержки студентов региональных ВУЗов Беларуси и Украины (с этого года).\n" +
                    "\n" +
                    "В первую очередь благодарим MikeMirzayanov и всех, кто причастен к развитию платформ Codeforces и Polygon. Не меньшая благодарность KAN за координацию — благодаря ему вы сможете понять наши задачи. А также всем нашим тренерам и родителям, которые научили нас делать все то, что мы умеем.\n" +
                    "\n" +
                    "Разбалловка обещает быть такой: 500 — 750 — 1000 — 1500 — 2000 — 2750.\n" +
                    "\n" +
                    "Всем удачи и чистого кода!\n" +
                    "\n" +
                    "UPD\n" +
                    "\n" +
                    "Поздравляем победителей рейтингового зачета:\n" +
                    "1. Yukikaze_\n" +
                    "2. lunabbit\n" +
                    "3. kamer\n" +
                    "4. Potassium_Fan\n" +
                    "5. 2018LZY\n" +
                    "\n" +
                    "И победителей общего зачета:\n" +
                    "1. pikmike\n" +
                    "2. dlalswp25\n" +
                    "3. tfg\n" +
                    "4. Heltion\n" +
                    "5. hank55663\n" +
                    "\n" +
                    "Разбор будет позже.\n" +
                    "\n" +
                    "UPD\n" +
                    "\n" +
                    "Разбор подъехал", 6),
            new Post(14, "Codeforces Round 666", "Hi Codeforces!\n" +
                    "\n" +
                    "stefdasca, koala_bear00 and I are very excited to announce our first contest Codeforces Round #666, which will take place воскресенье, 18 октября 2020 г. в 12:05. The round will be rated for participants with rating up to 2099.\n" +
                    "\n" +
                    "The tasks were written by me with help from stefdasca and koala_bear00 and we hope we compiled a very interesting contest with memorable tasks :)\n" +
                    "\n" +
                    "Special thanks to:\n" +
                    "\n" +
                    "antontrygubO_o for coordinating our round and pushing us to come up with more and more interesting tasks.\n" +
                    "\n" +
                    "dorijanlendvaj, kclee2172, Devil, * thenymphsofdelphi, jainbot27, Stelutzu, proggerkz, SleepyShashwat, bhaskarjoshi2001, AmShZ, Osama_Alkhodairy, raresdanut, katsurap_, A_N_D_Y, Usu and Miyukine for testing the round and providing useful feedback.\n" +
                    "\n" +
                    "MikeMirzayanov for awesome Codeforces and Polygon platforms. Thanks!\n" +
                    "\n" +
                    "You will be given 2 hours to solve 5 problems, good luck everyone and have fun!\n" +
                    "\n" +
                    "UPD 1: After the round you can watch videos explaining the solutions to the tasks on stefdasca's Youtube channel.\n" +
                    "\n" +
                    "UPD 2: The round was rescheduled, because of intersection with other scheduled contests.", 1)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        data.put("posts", POSTS);
    }
}
