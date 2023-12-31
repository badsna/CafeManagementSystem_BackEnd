PGDMP                         {            cafemgmtsys_db    12.16    12.16 (    5           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            6           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            7           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            8           1262    16875    cafemgmtsys_db    DATABASE     �   CREATE DATABASE cafemgmtsys_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
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
       public          postgres    false    203            9           0    0    bill_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.bill_id_seq OWNED BY public.bill.id;
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
       public          postgres    false    205            :           0    0    category_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;
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
       public          postgres    false    207            ;           0    0    product_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;
          public          postgres    false    206            �            1259    17102    reset_token    TABLE     �   CREATE TABLE public.reset_token (
    id integer NOT NULL,
    email character varying(255),
    expiration_date timestamp(6) without time zone,
    token character varying(255),
    user_id integer NOT NULL
);
    DROP TABLE public.reset_token;
       public         heap    postgres    false            �            1259    17100    reset_token_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reset_token_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.reset_token_id_seq;
       public          postgres    false    211            <           0    0    reset_token_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.reset_token_id_seq OWNED BY public.reset_token.id;
          public          postgres    false    210            �            1259    17047    users    TABLE       CREATE TABLE public.users (
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
       public          postgres    false    209            =           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
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
       public          postgres    false    206    207    207            �
           2604    17105    reset_token id    DEFAULT     p   ALTER TABLE ONLY public.reset_token ALTER COLUMN id SET DEFAULT nextval('public.reset_token_id_seq'::regclass);
 =   ALTER TABLE public.reset_token ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    211    210    211            �
           2604    17050    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    209    209            *          0    17017    bill 
   TABLE DATA           y   COPY public.bill (id, contact_number, created_by, email, name, payment_method, product_details, total, uuid) FROM stdin;
    public          postgres    false    203   Q+       ,          0    17028    category 
   TABLE DATA           ,   COPY public.category (id, name) FROM stdin;
    public          postgres    false    205   �,       .          0    17036    product 
   TABLE DATA           T   COPY public.product (id, description, name, price, status, category_fk) FROM stdin;
    public          postgres    false    207   :-       2          0    17102    reset_token 
   TABLE DATA           Q   COPY public.reset_token (id, email, expiration_date, token, user_id) FROM stdin;
    public          postgres    false    211   �/       0          0    17047    users 
   TABLE DATA           X   COPY public.users (id, contact_number, email, name, password, role, status) FROM stdin;
    public          postgres    false    209   /0       >           0    0    bill_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.bill_id_seq', 6, true);
          public          postgres    false    202            ?           0    0    category_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.category_id_seq', 9, true);
          public          postgres    false    204            @           0    0    product_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.product_id_seq', 13, true);
          public          postgres    false    206            A           0    0    reset_token_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.reset_token_id_seq', 29, true);
          public          postgres    false    210            B           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 9, true);
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
           2606    17110    reset_token reset_token_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reset_token
    ADD CONSTRAINT reset_token_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reset_token DROP CONSTRAINT reset_token_pkey;
       public            postgres    false    211            �
           2606    17055    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    209            �
           2606    17056 #   product fk275nu1ncohhfur6qhxiwrm3go    FK CONSTRAINT     �   ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk275nu1ncohhfur6qhxiwrm3go FOREIGN KEY (category_fk) REFERENCES public.category(id);
 M   ALTER TABLE ONLY public.product DROP CONSTRAINT fk275nu1ncohhfur6qhxiwrm3go;
       public          postgres    false    207    205    2723            *   [  x�풻N�0�g�)�̥��[�T5E�����m��RlW�B�;-U��t�9�ɯ�;!�Ĕq!����*\�8�J����7v�78�� Sn�?"SD���PT�J�ct�6V;נ(47��m7�8T��<V��z��];�5������V����.�>G�!:�m�s��e�^�8͘���;��u��^LMY��2�bA$�WY�Jh��ҫ��eQ�eL$dWYȢX
) i�0?��C������,۫N���x�)[�%��ֶg��l�3[��KhfM����0v��և1����)��[)�s������.�]�}������z�j3�F{\�b�1|C� ��F      ,   n   x�5���@�}_q_`��@+ZcbC���A��!�=���L&�;O����5��%���%\���]��<��.!v��g��� ����j(R��7������3�h�N v� "      .   x  x�UT�r�0<S_��h,�q�c�izI/m���Q�Ě"]>�Q�� �ؓE���.�Z|�5C	�I&8��G��m_�ő�D�ۉ�"�}�ܯgFF!�����t��q!����0.k��8U℗K�R+��� N�WZ����_�)=���!/AY��S�z%����Ec(�Х��#2w��-�p�]ǌWP_�o93���l��x�!�[rS�{^����"/N�*��/����.>�����J�YNh��?(�ۉ��ȯlL\�i����c��4��7���D�Y(Ä� ߴD����:v����7�	�>g�8�7u(>��xJ�G��fe�L�@	�dE%�8$�O�xf�G�����1���)��J����M���『�&w�S��J��t�D���CQ��uTr��x����gVeJ�7ȱ��q*<hu���%Ǎ�K`��0�[rn��w���n4hXp��{��J�k�<�>y�,[�ITɔ�"�M�
�%�v�`��o�����M���w�C%N:��s+)��cQ�S�.�>`vx����@}2�4P�V���4E������y�į��xc�����}����r��m4������.:f�-��whc����V�=����UQ��t{      2   ]   x����  �7TaK܃˗��u�D�?�P6�o/ޯwiW�����mh&�9�PX�]�&x�$QA%	HR�L)A�UY�4h7g����?      0   +  x���I��@ ���9���T\��F+��d�E��Ie�*����Wo>����fX�����W�Q��"��:ē7��f�G���Jo�V�	�oqbM���sF�N6n}�kڃh��Rj&�W��j�|����~��_,UAӕC�p��#��/��ԡ�zsZ(�0*ع��������(�tg�.�U���9!7wx�\2�N0��2�&%�z�,u��)��VJ0>BGC^��.�c_������3������Z��8l�:IGk�J�I��"��T�݌@�/�R/VRʇGe) p�MZ>YP1{%��*?J�"9�=��bҒ�{��|�Jox�<�O耥m�*IՅ�Wܳr�>�^U6e�B�۝>C��[���i�R_�1v|d�o����Om�5�@�T�~(�4�̂S���c��T�MR��5���E�5�[+$�ܳ�_4�(�~,�������ͣ�۳�e�m4<�/�����F����o�c�F��M2uw���%��N�[��n���~�F��$q�?h��!g��j���b:��O[IW     