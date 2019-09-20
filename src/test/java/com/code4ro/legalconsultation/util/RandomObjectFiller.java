package com.code4ro.legalconsultation.util;

import com.code4ro.legalconsultation.model.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RandomObjectFiller {

    public static  <T> T createAndFill(Class<T> clazz) {
        try {
            final T instance = clazz.getDeclaredConstructor().newInstance();
            for(final Field field: clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = getRandomValueForField(field);
                field.set(instance, value);
            }
            return instance;
        } catch (Exception e) {
            return null;
        }
    }

    private static Object getRandomValueForField(Field field) {
        final Class<?> type = field.getType();

        if(type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[RandomUtils.nextInt(0, enumValues.length)];
        } else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return RandomUtils.nextInt();
        } else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
            return RandomUtils.nextLong();
        } else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
            return RandomUtils.nextDouble();
        } else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
            return RandomUtils.nextFloat();
        } else if(type.equals(UUID.class)) {
            return UUID.randomUUID();
        } else if(type.equals(BigInteger.class)){
            return BigInteger.valueOf(RandomUtils.nextInt());
        } else if(type.equals(String.class)) {
            return getFieldWithContraint(field);
        } else if(type.equals(Date.class)) {
            int randomYear = RandomUtils.nextInt(1990, 2020);
            int randomDay = RandomUtils.nextInt(1, 366);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, randomYear);
            calendar.set(Calendar.DAY_OF_YEAR, randomDay);
            return calendar.getTime();
        }
        return createAndFill(type);
    }

    private static String getFieldWithContraint(final Field field) {
        final Email email = field.getAnnotation(Email.class);
        if (email != null) {
            return RandomStringUtils.randomAlphabetic(10) + "@email.com";
        }
        final Size size = field.getAnnotation(Size.class);
        if (size != null) {
            return RandomStringUtils.randomAlphabetic(size.min(), size.max());
        }
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static DocumentConsolidated buildRandomDocumentConsolidated(){
        DocumentMetadata metadata = RandomObjectFiller.createAndFill(DocumentMetadata.class);

        Article article1 = RandomObjectFiller.createAndFill(Article.class);
        Article article2 = RandomObjectFiller.createAndFill(Article.class);
        Chapter chapter1 = RandomObjectFiller.createAndFill(Chapter.class);
        chapter1.setArticles(List.of(article1, article2));
        article1.setArticleChapter(chapter1);
        article2.setArticleChapter(chapter1);

        DocumentBreakdown breakdown = RandomObjectFiller.createAndFill(DocumentBreakdown.class);
        breakdown.setChapters(List.of(chapter1));
        chapter1.setChapterDocument(breakdown);

        return new DocumentConsolidated(metadata, breakdown);
    }
}