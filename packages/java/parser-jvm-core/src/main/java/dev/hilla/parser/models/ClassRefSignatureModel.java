package dev.hilla.parser.models;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassRefTypeSignature;

public abstract class ClassRefSignatureModel extends AnnotatedAbstractModel
        implements SignatureModel, NamedModel,
        OwnedModel<Optional<ClassRefSignatureModel>> {
    private ClassInfoModel reference;
    private List<TypeArgumentModel> typeArguments;

    public static boolean is(AnnotatedParameterizedType actor,
            Class<?> target) {
        return is(actor, target.getName());
    }

    public static boolean is(AnnotatedParameterizedType actor,
            ClassInfo target) {
        return is(actor, target.getName());
    }

    public static boolean is(AnnotatedParameterizedType actor, String target) {
        return ((Class<?>) ((ParameterizedType) actor.getType()).getRawType())
                .getName().equals(target);
    }

    public static boolean is(AnnotatedType actor, Class<?> target) {
        return is(actor, target.getName());
    }

    public static boolean is(AnnotatedType actor, ClassInfo target) {
        return is(actor, target.getName());
    }

    public static boolean is(AnnotatedType actor, String target) {
        return actor instanceof AnnotatedParameterizedType
                ? is((AnnotatedParameterizedType) actor, target)
                : ((Class<?>) actor.getType()).getName().equals(target);
    }

    public static boolean is(Class<?> actor, Class<?> target) {
        return actor.equals(target);
    }

    public static boolean is(Class<?> actor, ClassInfo target) {
        return is(actor, target.getName());
    }

    public static boolean is(Class<?> actor, String target) {
        return actor.getName().equals(target);
    }

    public static boolean is(ClassRefTypeSignature actor, Class<?> target) {
        return is(actor, target.getName());
    }

    public static boolean is(ClassRefTypeSignature actor, ClassInfo target) {
        return is(actor, target.getName());
    }

    public static boolean is(ClassRefTypeSignature actor, String target) {
        return actor.getFullyQualifiedClassName().equals(target);
    }

    public static ClassRefSignatureModel of(
            @Nonnull ClassRefTypeSignature origin) {
        return Objects.requireNonNull(origin).getSuffixes().size() > 0
                ? new ClassRefSignatureSourceModel.Suffixed(origin)
                : new ClassRefSignatureSourceModel.Regular(origin);
    }

    public static ClassRefSignatureModel of(@Nonnull Class<?> origin) {
        return new ClassRefSignatureReflectionModel.Bare(
                Objects.requireNonNull(origin));
    }

    public static ClassRefSignatureModel of(@Nonnull AnnotatedType origin) {
        return ClassRefSignatureReflectionModel.Annotated
                .of(Objects.requireNonNull(origin));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ClassRefSignatureModel)) {
            return false;
        }

        var other = (ClassRefSignatureModel) obj;

        return getName().equals(other.getName())
                && getOwner().equals(other.getOwner())
                && getTypeArguments().equals(other.getTypeArguments())
                && getAnnotations().equals(other.getAnnotations());
    }

    public ClassInfoModel getClassInfo() {
        if (reference == null) {
            reference = prepareClassInfo();
        }

        return reference;
    }

    public List<TypeArgumentModel> getTypeArguments() {
        if (typeArguments == null) {
            typeArguments = prepareTypeArguments();
        }

        return typeArguments;
    }

    public Stream<TypeArgumentModel> getTypeArgumentsStream() {
        return getTypeArguments().stream();
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + 7 * getTypeArguments().hashCode()
                + 23 * getAnnotations().hashCode() + 53 * getOwner().hashCode();
    }

    @Override
    public boolean isBoolean() {
        return getClassInfo().isBoolean();
    }

    @Override
    public boolean isByte() {
        return getClassInfo().isByte();
    }

    @Override
    public boolean isCharacter() {
        return getClassInfo().isCharacter();
    }

    @Override
    public boolean isClassRef() {
        return true;
    }

    @Override
    public boolean isDate() {
        return getClassInfo().isDate();
    }

    @Override
    public boolean isDateTime() {
        return getClassInfo().isDateTime();
    }

    @Override
    public boolean isDouble() {
        return getClassInfo().isDouble();
    }

    @Override
    public boolean isEnum() {
        return getClassInfo().isEnum();
    }

    @Override
    public boolean isFloat() {
        return getClassInfo().isFloat();
    }

    @Override
    public boolean isInteger() {
        return getClassInfo().isInteger();
    }

    @Override
    public boolean isIterable() {
        return getClassInfo().isIterable();
    }

    @Override
    public boolean isJDKClass() {
        return getClassInfo().isJDKClass();
    }

    @Override
    public boolean isLong() {
        return getClassInfo().isLong();
    }

    @Override
    public boolean isMap() {
        return getClassInfo().isMap();
    }

    @Override
    public boolean isNativeObject() {
        return getClassInfo().isNativeObject();
    }

    @Override
    public boolean isOptional() {
        return getClassInfo().isOptional();
    }

    @Override
    public boolean isShort() {
        return getClassInfo().isShort();
    }

    @Override
    public boolean isString() {
        return getClassInfo().isString();
    }

    public void setReference(ClassInfoModel reference) {
        this.reference = reference;
    }

    protected abstract List<AnnotationInfoModel> prepareAnnotations();

    protected abstract ClassInfoModel prepareClassInfo();

    protected abstract List<TypeArgumentModel> prepareTypeArguments();
}
