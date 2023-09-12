PGDMP         "        
        {            cafemgmtsys_db    12.16    12.16 !    )           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            *           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            +           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ,           1262    16875    cafemgmtsys_db    DATABASE     �   CREATE DATABASE cafemgmtsys_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE cafemgmtsys_db;
                postgres    false            �            1259    17017    bill    TABLE     D  CREATE TABLE public.bill (
    id integer NOT NULL,
    contact_number character varying(255),
    created_by character varying(255),
    email character varying(255),
    name character varying(255),
    payment_method character varying(255),
    product_details json,
    total integer,
    uuid character varying(255)
);
    DROP TABLE public.bill;
       public         heap    postgres    false            �            1259    17015    bill_id_seq    SEQUENCE     �   CREATE SEQUENCE public.bill_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.bill_id_seq;
       public          postgres    false    203            -           0    0    bill_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.bill_id_seq OWNED BY public.bill.id;
          public          postgres    false    202            �            1259    17028    category    TABLE     [   CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255)
);
    DROP TABLE public.category;
       public         heap    postgres    false            �            1259    17026    category_id_seq    SEQUENCE     �   CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.category_id_seq;
       public          postgres    false    205            .           0    0    category_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;
          public          postgres    false    204            �            1259    17036    product    TABLE     �   CREATE TABLE public.product (
    id integer NOT NULL,
    description character varying(255),
    name character varying(255),
    price integer,
    status character varying(255),
    category_fk integer NOT NULL
);
    DROP TABLE public.product;
       public         heap    postgres    false            �            1259    17034    product_id_seq    SEQUENCE     �   CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.product_id_seq;
       public          postgres    false    207            /           0    0    product_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;
          public          postgres    false    206            �            1259    17047    users    TABLE       CREATE TABLE public.users (
    id integer NOT NULL,
    contact_number character varying(255),
    email character varying(255),
    name character varying(255),
    password character varying(255),
    role character varying(255),
    status character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    17045    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    209            0           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    208            �
           2604    17020    bill id    DEFAULT     b   ALTER TABLE ONLY public.bill ALTER COLUMN id SET DEFAULT nextval('public.bill_id_seq'::regclass);
 6   ALTER TABLE public.bill ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    202    203    203            �
           2604    17031    category id    DEFAULT     j   ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);
 :   ALTER TABLE public.category ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            �
           2604    17039 
   product id    DEFAULT     h   ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);
 9   ALTER TABLE public.product ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    207    206    207            �
           2604    17050    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    208    209                       0    17017    bill 
   TABLE DATA           y   COPY public.bill (id, contact_number, created_by, email, name, payment_method, product_details, total, uuid) FROM stdin;
    public          postgres    false    203   c#       "          0    17028    category 
   TABLE DATA           ,   COPY public.category (id, name) FROM stdin;
    public          postgres    false    205   >$       $          0    17036    product 
   TABLE DATA           T   COPY public.product (id, description, name, price, status, category_fk) FROM stdin;
    public          postgres    false    207   �$       &          0    17047    users 
   TABLE DATA           X   COPY public.users (id, contact_number, email, name, password, role, status) FROM stdin;
    public          postgres    false    209   ;'       1           0    0    bill_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.bill_id_seq', 2, true);
          public          postgres    false    202            2           0    0    category_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.category_id_seq', 9, true);
          public          postgres    false    204            3           0    0    product_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.product_id_seq', 13, true);
          public          postgres    false    206            4           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 4, true);
          public          postgres    false    208            �
           2606    17025    bill bill_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.bill
    ADD CONSTRAINT bill_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.bill DROP CONSTRAINT bill_pkey;
       public            postgres    false    203            �
           2606    17033    category category_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pkey;
       public            postgres    false    205            �
           2606    17044    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    207            �
           2606    17055    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    209            �
           2606    17056 #   product fk275nu1ncohhfur6qhxiwrm3go    FK CONSTRAINT     �   ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk275nu1ncohhfur6qhxiwrm3go FOREIGN KEY (category_fk) REFERENCES public.category(id);
 M   ALTER TABLE ONLY public.product DROP CONSTRAINT fk275nu1ncohhfur6qhxiwrm3go;
       public          postgres    false    207    205    2715                �   x��M
�0���)B�*MF�J,.<��k����L\�xw[Qq������O1'iÑVl�j0˲I������ك�,Gڱ�Eڍ���ڴ����"'d����M�����`�Ο�R��Ҙ�q<al�S%�=�P��"�����7|:��7�,����Ŵb)Dlj���@�I�& <����X�Dk�|9��� �D�      "   n   x�5���@�}_q_`��@+ZcbC���A��!�=���L&�;O����5��%���%\���]��<��.!v��g��� ����j(R��7������3�h�N v� "      $   o  x�US�r�0<S_��h"�I�c�izI/m���Q�Ě"]>�Q�� %ۓ�D ��.P�/���f(AZ#���:��{"��?;�ނhDp�Įh�ϳ
#�N\8)}4 h\ȁ��w6���-N�8���TƊ]s���1Sc�"��Lʕ�c��`��(OW�m��*�!�h�7ܽ8j�^ɭ����t�F�Ƌe�砬��9g�����4ţxU�J ti���;�V��9��c%VP_�o93���k�'���[�X�{Z��<�"/��*��/����.>�����J�Yh���2v��&�431p��-w1��,'M2�M�h+Qg�0!�)�/-�cde���2�����\M	�3@�C�����Q1�Y�)�.F�!YQ�w�ý�~/l���#�?�41�?QIZ��k��}0-�K�Vݱ�);Q_w걨k�6*��t���`��2�܌��X����:�]���Fi�%��l�-9�T�;��
}7�	4,����=�f%ǵm>�<p��O�$�dJh�ϱdB��{;a�e�7�b�A��&@�C��(�J�ul���R�7NOE�cN������z������@[��k���ھ�W&J��r�Sh��aDs�5�]E�L_zm      &   %  x�u��o�0��3��+P�v[a����d�����h��~����/y>��n@Ӳ�IWrr�-�,뙑�)["�(��6(3��tm�u�=�N6�����a�d]�1HO�3�E�m��<��+���B��l�I��7W����Tױ%�d�7�d.S����(y���d�l�,�����+���5�c���	l����YcpV�ݺ�C,�-�]����
�`¸���V��r�g��7ИjDN�W ���
n���:B�� I,X��~���im��6���Iޑ@U�D��(     