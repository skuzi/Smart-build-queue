# Smart-build-queue
JetBrains Internship test project

## Пререквизиты
Java 11
## Инструкция к запуску
Склонировать репозиторий, выполнить `./gradlew run` (лучше с флагом `--console=plain` для большего удобства), вводить числа в консоль в следующем формате.
### Формат ввода
На первой строке находится число `n` -- количество поездов.  
В следующих `n` строках написано по 1 целому и 3 вещественных числа -- номер поезда, время прибытия, время разгрузки, награда за разгрузку. Далее момент окончания разгрузки будем называть "отбытием" поезда.


## Сущности в решении
`Train` -- представление поезда, имеет время прибытия, отбытия, разгрузки, прибыль за разгрузку, номер поезда  
`Schedule` -- представление ответа, хранит в себе прибыль и список поездов, которые необходимо разгрузить, чтобы получить эту прибыль  
`WorkerPolicy` -- интерфейс, содержит метод `findBestSchedule(List<Train>)`, который по списку поездов вернет соответствущий экземпляр `Schedule`  
У интерфейса есть два наследника -- `OptimalPolicy` и `GreedyPolicy`

### Optimal policy
В решении делаем предположение, что если один поезд отбывает в момент времени `t`, а второй прибывает в момент времени `t`, то мы можем разгрузить оба этих поезда. Также делается предположение, что время на разгрузку поезда положительное, иначе можно было бы разгрузить сколь угодно много поездов одновременно.  

Решение основано на методе "динамическое программирование".  

Будем для каждого важного события считать максимальную прибыль, которую мы могли бы получить, если бы закончили разгрузку в это событие или раньше. Важные события -- это прибытие поезда и отбытие поезда, в другие моменты времени не происходит событий, которые могли бы повлиять на прибыль.  

Для этого положим все эти события в список и отсортируем этот список по времени, причем событие вида "отбытие поезда" будет происходить раньше, чем "прибытие поезда", это необходимо для того, чтобы можно было начать разгружать следующий поезд, как только отбыл предыдущий. 
После этого заведем массив `dp` того же размера, что и количество событий. В `dp[i]` будет находиться максимальная прибыль, которую можно получить, если закончить разгрузку в событии с номером `i` (или до этого события). Заметим, что `dp[i]` неубывает.  

### Начальное состояние:  
`dp[0] = 0` -- мы знаем, что нулевое событие -- это прибытие поезда, и мы не можем получить от этого никакую прибыль  
### Переходы:  
* Если событие `i` -- это прибытие какого-то поезда, то `dp[i] = dp[i - 1]` (никакой поезд не отбыл, прибыль получить невозможно)
* Если событие `i` -- это отбытие поезда, то `dp[i] = max(dp[i - 1], dp[arrival(i)] + cost(i))`. Этот переход означает, что существует выбор -- либо разгружать этот поезд, либо нет. Если мы решаем не разгружать поезд, то прибыль будет такой же, как и в прошлом событии. Если поезд все-таки решено разгрузить, то нужно найти, какому событию соответствует прибытие этого поезда, понять, сколько прибыли можно получить до начала разгрузки этого поезда и прибавить к общей прибыли. Здесь `arrival(i)` -- это событие, соответствующее прибытию этого поезда, а `cost(i)` -- прибыль, которая будет получена за разгрузку этого поезда.  

Максимальная прибыль находится в последнем элементе `dp`. Для восстановления списка поездов пользуемся обратными переходами -- узнаем, из какого состояния массива `dp` был совершен переход в это состояние, и перейдем в него. Если этот переход задействовал разгрузку поезда, то добавим этот поезд в список. Можно заметить, что эти поезда находятся в невозрастающем порядке по времени прибытия, поэтому для удобства восприятия список в конце алгоритма разворачивается.  

Алгоритм также находит расписание с минимальным временем окончания среди всех оптимальных -- для этого при восстановлении ответа будем переходить в предыдущее состояние всегда, когда прибыль в нем не меньше, чем прибыль, которая бы получилась при разгрузке поезда. 

#### Время работы
В алгоритме используется сортировка и несколько линейных проходов. Для того, чтобы знать, какому событию вида (прибытие поезда) соответствует событие (отбытия поезда) используется `HashMap`, его временная сложность `O(1)` в среднем на операции `get` и `put`. Итого временная сложность -- `O(n log n)`, где `n` -- количество поездов.
#### Используемая память
Алгоритм использует `O(n)` памяти, где `n` -- количество поездов.
#### Преимущества
Алгоритм находит оптимальный ответ за хорошее время, использует линейное количество памяти, прост для понимания другим человеком.
#### Недостатки
* Узкое место алгоритма -- сортировка. Если бы было известно, что количество возможных времен мало, то было бы возможно воспользоваться более быстрыми методами сортировки -- например, сортировка подсчетом, что позволило бы значительно снизить время работы в случае, когда поездов очень много, а временных промежутков мало.  
* Алгоритм плохо подвергается распараллеливанию, т.к. переходы должны выполняться последовательно. Возможно, есть вариант с решением задачи на нескольких выделенных промежутках времени (т.е. разбить все времена на несколько промежутков, на каждом решить оптимально и потом объединить ответы), но тогда невозможно гарантировать оптимальность в общем случае. 
* Данный алгоритм плохо справляется с изменяемым расписанием -- т.е. при внезапном добавлении или удалении поезда представляется затруднительным за быстрое время (например, за `O(1)`) пересчитать ответ.
#### Источник
Школьный и университетский курсы алгоритмов и структур данных. 


### Greedy policy
Также написан (в основном, для тестирования) жадный алгоритм.  
Сортирует все поезда по возрастанию времени прибытия, потом проходит по списку поездов и делает следующее:
1. Если список поездов в ответе пуст, кладет текущий поезд в список.
2. Если список поездов в ответе не пуст и текущий поезд прибывает позже, чем заканчивается разгрузка последнего поезда в ответе, кладет этот поезд в список.
3. Если список поездов в ответе не пуст и текущий поезд прибывает раньше, чем заканчивается разгрузка последнего поезда в ответе, кладет в список поезд с максимальной прибылью из этих двух (т.е. если текущий поезд строго "лучше" последнего в списке, то удаляет последний в списке и кладет этот в список, иначе ничего не делает).

Этот алгоритм не является оптимальным, контрпример:
```
3  
1 1 3 3   
2 2 4 5    
3 5 2 4
```
В данном случае будет выгодно взять первый и последний поезда, т.к. их суммарная стоимость 7, но алгоритм возьмет только второй поезд.
