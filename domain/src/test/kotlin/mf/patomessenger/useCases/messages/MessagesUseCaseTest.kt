package mf.patomessenger.useCases.messages

import io.mockk.mockk
import mf.patomessenger.configurations.EXTRA_WORD
import mf.patomessenger.configurations.EXTRA_WORD_SPACING
import org.junit.Test


class MessagesUseCaseTest {
    private var useCaseImpl = MessagesUseCase(mockk(), mockk())

    val text =
        "Kiedys obudzilem sie w nocy byla zima...mieszkałem na osiedlu w bloku na 4 pietrze, lezalem bez ruchu, " +
                "swiatlo latarni odbijalo sie od pokrytej sniegiem ulicy, wszedzie bylo tak bialo, ale uswiadomilem sobie jaki to dzwiek mnie obudzil, " +
                "dobiegal jakby z smietnika, taki skrzek ptaka i drapanie, ten dziwek byl taki glosny, ze pomyslalem ze zaraz jakis starszy frustrat wyjdzie na balkon i cos ryknie.. " +
                "albo zadzwoni na policje, ale nic takiego sie nie stalo, ten dzwiek kraczenia polaczonego ze skowytem i drapaniem byl jednostajny i miarowy i doprowadzal mnie do takej skrajnosci ze mowilem sobie tylko: " +
                "przeciez to sie nie moze dziac naprawde, to nie jest horror tylko prawdziwe zycie, serio mialem juz łzy w oczach , nie wiem ile lezalem bez ruchu, ale gdy kraczenie troche ucichlo zebralem sie w sobie i " +
                "delikatnie podszedlem do okna, na smietniku nic sie nie dzialo, postanowilem wiec ze uchyle troche firanke i zobacze dokladniej, chwycilem lekko za krawedz ... JEBBBBB KRAAAAAA jak cos nie uderzy w szybe, " +
                "chwyci mnie za reke, patrze a tu Cowiek Małpa najwiekszy zbrodniarz wojenny, zostaw mnie!!! darlem sie jak głupi a on tylko: KRAAAAAA KRAAAAAA UUUUUU... wpadłem do pokoju, a on stanął przede mna na parapecie " +
                "w calej okazalosci, szybko wybieglem z mieszkania, otworzylem szafke na bezpieczniki na korytarzu chcac urwac drzwiczki aby miec czym sie bronic, ale cowiek małpa juz byl za mna KRAAAAAAAAAAA, " +
                "odskoczylem... a on jak uderzył w te bezpieczniki, w calym bloku zamigotalo swiatlo, a ja korzystajac z okazji zamknalem drzwiczki i zakleilem je guma do żucia, " +
                "a on tylko wył i prychal, po czym sie uspokoil, a ja wrocilem do swojego lozka...Cowiek Małpa siedzi juz w budce na bezpieczniki drugi rok.. a ja tylko modle sie zeby nie bylo jakiejs awarii w bloku..."

    @Test
    fun testExtraWordApplier() {
        var success = true
        with(useCaseImpl) {
            text.appendExtraWord()
                .split(" ")
                .mapIndexed { index, word -> Pair(first = index, second = word) }
                .filter { it.second.equals(EXTRA_WORD, true) }
                .also { pairList ->
                    for (i in 1 until pairList.size) {
                        if (pairList[i - 1].first + EXTRA_WORD_SPACING > pairList[i].first) {
                            success = false
                            break
                        }
                    }
                }
        }
        assert(success)
    }
}