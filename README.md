
## ClipClapCharge Framework  para Android##

ClipClap te permite incorporar la acción de pagar en tu aplicación Android de forma fácil y rápida. Solo debes agregar el framework que te brindamos y los pagos serán gestionados por la aplicación Billetera de ClipClap.
Te recordamos que para poder hacer de esta integración debes descargar la aplicación de Billetera ClipClap en la Google Play.

## Prerrequisitos ##

 1. ***Tener una cuenta ClipClap Datáfono:***
Para poder realizar la integración con ClipClap debes primero tener una cuenta en ClipClap Datáfono, puedes hacer el proceso de registro siguiendo este [link](https://clipclap.co/) o desde la misma aplicación ClipClap Datáfono.

 2. ***Tener el secretKey de tu negocio:***
Una vez tengas tu usuario Datáfono, tendrás que tener a la mano el “secreKey” de tu negocio, puedes consultar los pasos para adquirirlos en detalle [aquí](https://clipclap.co/).

 3. **ClipClap Billetera para tus clientes:**
Para que tus usuarios puedan acceder al evento de pago de ClipClap deben tener instalada la aplicación Billetera, esta permitirá realizar los pagos de forma rápida y segura para tus clientes.

 4. ***Entorno de Prueba y Entorno de Producción:***
Recuerda que puedes cambiar entre entorno de prueba y de producción, para llevar un mayor control de tu integración. puedes aprender cómo hacerlo en el siguiente [link](https://clipclap.co/).


## Integración ##

Sigue los siguientes pasos para conocer cómo se debe integrar el framework de pago ClipClap en tu aplicación Android:

**Paso 1: En el proyecto de Android Studio de tu aplicación integra el framework así:**

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/1.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/2.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/3.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/4.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/5.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/6.png)

![enter image description here](http://www.clipclap.co/docs/tutorials/android/img/7.png)


**Paso 2: Configurar el cobro.**

Importa el *ClipClapCharge framework* en la clase donde la vayas a usar :

    import sdk.clipclap.clipclapcharge.CCBilleteraPayment;
    import sdk.clipclap.clipclapcharge.PayAndGo;
    import sdk.clipclap.clipclapcharge.SaveTokenListener;

Incluye este botón en el xml de tu vista:

    <sdk.clipclap.clipclapcharge.PayAndGo
               android:layout_width="match_parent"
               android:layout_height="wrap_content"
               android:text="Pagar"
               android:id="@+id/button" />

Crea el butón de pago:

	final PayAndGo paymentButton = (PayAndGo)findViewById(R.id.button);
	
Hay dos forma de crear un cobro para que ClipClap Billetera lo gestione:

 1) *Forma 'producto por producto':* Esta opción permite agregar al cobro productos de forma individual especificando su nombre, precio, cantidad y el impuesto que se le aplica al producto. Así: 
    
    paymentButton.setOnClickListener(new View.OnClickListener() {
          
	    @Override
	    public void onClick(View v) {
						
			//Configura tu deep link para que ClipClap Billetera pueda responderte
            PayAndGo.urlCallback = "YOUR_SCHEME://YOUR_DEEP_LINK"; 
            
            //Crea un nuevo pago con tu llave secreta
			CCBilleteraPayment ccPayment = new CCBilleteraPayment("YOUR_SECRET_KEY");
    ​
			//Por cada producto haga esto
		    string nombreProducto = @"Camisa Polo";
		    int precio = 25000;
		    int cantidad = 3;
            ccPayment.addItem(name, count, value, CCBilleteraPayment.IVA_REGULAR_16_);

			//Después de agregar tus productos dale a nuestra librería el JSON con la 
			//información
			PayAndGo.jsonObject = ccPayment.getJSON();        
	   }

2) *Forma 'total-impuesto-propina':* Esta opción permite definir el total a cobrar de forma inmediata especificando el total a cobrar sin impuestos, el impuesto sobre el total y de forma opcional la propina. Así:

	paymentButton.setOnClickListener(new View.OnClickListener() {
          
	    @Override
	    public void onClick(View v) {
						
			//Configura tu deep link para que ClipClap Billetera pueda responderte
            PayAndGo.urlCallback = "YOUR_SCHEME://YOUR_DEEP_LINK"; 
            
            //Crea un nuevo pago con tu llave secreta
			CCBilleteraPayment ccPayment = new CCBilleteraPayment("YOUR_SECRET_KEY");
    ​
		    //Agregando total sin impuesto, descripción, impuesto y propina
			string descripcion = @"Dos perros calientes y una gaseosa";
		    int totalSinImpuesto = 20000;
		    int impuesto = 1600; //Consumo Regular del 8% sobre el total sin impuesto.
		    int propina = 2000   //Esto es opcional.

			//Así para SI incluir propina.
		    ccPayment.addTotal (descripcion, totalSinImpuesto, impuesto, propina);
	    
		    //Así para NO incluir propina.
		    ccPayment.addTotal (descripcion, totalSinImpuesto, impuesto, 0); 
		    
			//Después de agregar tus productos dale a nuestra librería el JSON con la 
			//información
			PayAndGo.jsonObject = ccPayment.getJSON();        
	   }
                                             
> ***Nota:*** Estas dos formas de crear el cobro son mutuamente excluyentes. Si usted utiliza ambas formas al mismo tiempo, la *forma 'total-impuesto-tip'* prevalece sobre la *forma 'producto-por-producto'*.

**Paso 3: Decirle a ClipClap Billetera que realice el cobro**

	//Implementa este código para de obtener de ClipClap un token único para este cobro. Hasta este momento todavía el cobro no se ha hecho efectivo.

	paymentButton.setSaveTokenListener(new SaveTokenListener() {
            
	    @Override
		    public void saveToken(String token) {
               
	             //Antes de llamar a ClipClap Billetera guarda el 'token' retornado aquí en tu sistema de información.
                
                //LLamando a ClipClap Billetera para que gestione el cobro.
               try {
                    Uri uri = Uri.parse(button.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                 //LLama a la PlayStore si no está instalada
                    Uri uri = Uri.parse(PayAndGo.PLAYSTORE);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
		
> ***IMPORTANTE:*** Es recomendable guardar el 'token' ya que con éste usted puede relacionar el cobro con su sistema de información.


## Tipos de impuesto ##

    IVA_REGULAR_16_ => IVA Regular del 16%
    IVA_REDUCIDO_5 => IVA Reducido del 5%
    IVA_EXCENTO_0 => IVA Excento del 0%
    IVA_EXCLUIDO_0 => IVA Excluído del 0%
    CONSUMO_REGULAR_8 => Consumo Regular 8%
    CONSUMO_REDUCIDO_4 => Consumo Reducido 4%
    IVA_AMPLIADO_20 => IVA Ampliado 20%

## Respuesta por parte de ClipClap Billetera ##

Cuando ClipClap Billetera a ha finalizado el cobro, este responde de tres maneras a la aplicación que solicitó sus servicios. Así:

Si el cobro se realizó exitosamente:

    "YOUR_SCHEME://YOUR_DEEP_LINK?response=ok"

Si el cobro fue rechazado por el cliente:
  
    "YOUR_SCHEME://YOUR_DEEP_LINK?response=cancel" //El cobro fue rechazado por el cliente.

Si hubo un error realizando el cobro:

    "YOUR_SCHEME://YOUR_DEEP_LINK?response=error&message=Mostrar este error en tu aplicación"




