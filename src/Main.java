import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static AtomicInteger TextLength3 = new AtomicInteger();
    public static AtomicInteger TextLength4 = new AtomicInteger();
    public static AtomicInteger TextLength5 = new AtomicInteger();

    public static void addToLength(String word){
        int size = word.length();

        switch (size) {
            case 3 -> TextLength3.getAndIncrement();
            case 4 -> TextLength4.getAndIncrement();
            case 5 -> TextLength5.getAndIncrement();
        }
    }

    public static boolean isSame(String text){

        char first = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (first != c) {
                return false;
            }
        }
        return true;
    }

    public static void palindrome(String[] text) {

        for (String word : text) {
            if (word.contentEquals(new StringBuilder(word).reverse())) {
                if (!isSame(word)){
                    addToLength(word);
                }
            }
        }
    }

    public static void same(String [] text) {
        for (String word : text) {
            if (isSame(word)){
                addToLength(word);
            }
        }
    }

    public static void increase(String[] text) {
        for (String word : text) {
            boolean isIncreace = true;
            for (int i = 0; i < word.length() - 1; i++) {
                if (word.charAt(i) > word.charAt(i + 1)) {
                    isIncreace = false;
                    break;
                }
            }
            if (isIncreace && !isSame(word)) {
                addToLength(word);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(null,() -> palindrome(texts));
        Thread thread2 = new Thread(null,() -> same(texts));
        Thread thread3 = new Thread(null,() -> increase(texts));

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длинной 3: " + TextLength3 + " шт.");
        System.out.println("Красивых слов с длинной 4: " + TextLength4 + " шт.");
        System.out.println("Красивых слов с длинной 5: " + TextLength5 + " шт.");
    }
}