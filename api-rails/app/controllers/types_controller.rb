class TypesController < ApplicationController

  #Returns list of types. 
  # GET /types
  # GET /types.json
  def index
    @types = Type.all
    render json: @types if stale?(etag: @types.all, last_modified: @types.maximum(:updated_at))
  end

  #Returns device with given id knowing he belongs to which user and room.
  # GET /devices/1
  # GET /devices/1.json
  def show
    @type= Type.find(params[:id])
    render json: @type if stale?(@type)
  end

   def find 
    @type = Type.where(:name => params[:name])
    render json: @type if stale?(etag: @type.all, last_modified: @type.maximum(:updated_at))

    # render json: @type if stale?(@type)
 end

  def update
    if @type.update(type_params)
      head :no_content
    else
      render json: @type.errors, status: :unprocessable_entity
    end
  end
  #Creates type with given parameters.
  # POST /types
  # POST /types.json
  def create
    @type = Type.new(params.require(:type).permit(:name, :clickertype))  
      if @type.save
      render json: @type, status: :created
    else
      render json: @type.errors, status: :unprocessable_entity
    end
  end

  #Deletes type with given id.
  # DELETE /types/1
  # DELETE /types/1.json
  def destroy
    @type=Type.find(params[:id])
    @type.destroy
    head :no_content
  end

  private
  # Never trust parameters from the scary internet, only allow the white list through.
  def type_params
    params.require(:type).permit(:name ,:clickertype) 
  end




end
