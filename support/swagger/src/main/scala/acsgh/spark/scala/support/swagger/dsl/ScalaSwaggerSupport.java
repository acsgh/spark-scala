package acsgh.spark.scala.support.swagger.dsl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumeratum.EnumEntry;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.*;
import scala.collection.immutable.Vector;
import scala.collection.immutable.VectorIterator;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScalaSwaggerSupport extends ModelResolver {

    public static void register() {
        ModelConverters.getInstance().addConverter(new ScalaSwaggerSupport(Json.mapper()));
    }

    public ScalaSwaggerSupport(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        Optional<io.swagger.v3.oas.annotations.media.Schema> schema = findEnumAnnotation(type, io.swagger.v3.oas.annotations.media.Schema.class);

        return findEnumSchema(type, context, schema)
                .or(() -> findPrimitiveSchema(schema))
                .orElseGet(() -> findDefaultSchema(type, context, chain));

    }

    private Schema findDefaultSchema(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (chain.hasNext()) {
            return chain.next().resolve(type, context, chain);
        } else {
            return null;
        }
    }

    private Optional<Schema> findEnumSchema(AnnotatedType type, ModelConverterContext context, Optional<io.swagger.v3.oas.annotations.media.Schema> schema) {
        try {
            JavaType _type = Json.mapper().constructType(type.getType());
            if (_type != null) {
                Class<?> enumClass = _type.getRawClass();

                if (EnumEntry.class.isAssignableFrom(enumClass)) {
                    String enumName = enumClass.getSimpleName();

                    if (!context.getDefinedModels().containsKey(enumName)) {
                        StringSchema enumModel = new StringSchema();

                        scala.collection.Iterator iterator = ((Vector) enumClass.getDeclaredMethod("values").invoke(null)).iterator();

                        while (iterator.hasNext()) {
                            enumModel.addEnumItem(iterator.next().toString());
                        }

                        transferCommonValues(enumModel, schema);

                        context.defineModel(enumName, enumModel);
                    }

                    StringSchema refSchema = new StringSchema();
                    refSchema.$ref("#/components/schemas/" + enumName);
                    return Optional.of(refSchema);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private Optional<Schema> findPrimitiveSchema(Optional<io.swagger.v3.oas.annotations.media.Schema> schema) {
        return schema.flatMap(s -> {
            String schemaType = schema.get().type();

            if (schemaType.equals("scala.Byte") || schemaType.equalsIgnoreCase("Byte")) {
                StringSchema result = new StringSchema();
                result.format("byte");
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.Long") || schemaType.equalsIgnoreCase("Long")) {
                IntegerSchema result = new IntegerSchema();
                result.format("int64");
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.Int") || schemaType.equalsIgnoreCase("Int")) {
                IntegerSchema result = new IntegerSchema();
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.Float") || schemaType.equalsIgnoreCase("float")) {
                NumberSchema result = new NumberSchema();
                result.format("float");
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.Double") || schemaType.equalsIgnoreCase("double")) {
                NumberSchema result = new NumberSchema();
                result.format("double");
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.Boolean") || schemaType.equalsIgnoreCase("boolean")) {
                BooleanSchema result = new BooleanSchema();
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else if (schemaType.equals("scala.BigDecimal") || schemaType.equalsIgnoreCase("BigDecimal")) {
                NumberSchema result = new NumberSchema();
                transferCommonValues(result, schema);
                return Optional.of(result);
            } else {
                return Optional.empty();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> Optional<T> findEnumAnnotation(AnnotatedType type, Class<T> annotation) {
        return Optional.ofNullable(type.getCtxAnnotations())
                .map(Arrays::stream)
                .flatMap(b -> b.filter(a -> a.annotationType() == annotation).findFirst())
                .map(c -> (T) c);
    }

    private void transferCommonValues(Schema<?> schema, Optional<io.swagger.v3.oas.annotations.media.Schema> annotation) {
        annotation.ifPresent(a -> {
            setIfValid(a.defaultValue(), schema::setDefault);
            setIfValid(a.minimum(), BigDecimal::new, schema::minimum);
            setIfValid(a.maximum(), BigDecimal::new, schema::maximum);
            setIfValid(a.minLength(), schema::minLength);
            setIfValid(a.maxLength(), schema::maxLength);
            setIfValid(a.format(), schema::format);
            setIfValid(a.example(), schema::example);
        });
    }

    private void setIfValid(String value, Consumer<String> consumer) {
        if ((value != null) && (!value.isEmpty())) {
            consumer.accept(value);
        }
    }

    private <T> void setIfValid(String value, Function<String, T> converter, Consumer<T> consumer) {
        if ((value != null) && (!value.isEmpty())) {
            consumer.accept(converter.apply(value));
        }
    }

    private <T> void setIfValid(int value, Consumer<Integer> consumer) {
        if ((value > 0) && (value < Integer.MAX_VALUE)) {
            consumer.accept(value);
        }
    }
}
